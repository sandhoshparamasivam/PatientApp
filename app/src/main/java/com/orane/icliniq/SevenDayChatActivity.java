package com.orane.icliniq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orane.icliniq.Model.Model;
import com.orane.icliniq.network.NetCheck;
import com.orane.icliniq.utils.RetrofitService;
import com.orane.icliniq.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SevenDayChatActivity extends AppCompatActivity {
    LinearLayout someone_layout,detailsLayout,familyLyt,selectSpecialityLyt, parentLayout;
    HorizontalScrollView familyScroll;
    ImageView img_edit_icon,imgDownArrow,imgUpArrow,imgRemove;
    TextView tvFamilyName,tvFamilyAge,tvFamilyHeight,tvFamilyWeight,tvSpecialityName;
    EditText edtQuery;
    Button btnIcq7;
    String  radio_id = "0",myself_id, relationTypeVal ="",Log_Status,spec_val,query_txt;
    Map<String, String> family_map = new HashMap<String, String>();
    ProgressDialog dialog;
    SharedPreferences sharedpreferences;
    TextView tvFee;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Login_Status = "Login_Status_key";
    public String family_list, wt_name, invId, invFee,   ht_name2, ht_val2, qid, draft_qid;
    String personaIdVal, invStrFee, strFeesTxt, icq100IdVal,  infFor = "icq100",  mem_name, height_name, tit_id, age_val, gender_val, height_val, weight_val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_day_chat);
        someone_layout=findViewById(R.id.someone_layout);
        detailsLayout=findViewById(R.id.detailsLayout);
        familyLyt=findViewById(R.id.familyLyt);
        parentLayout =findViewById(R.id.parent_layout);
        selectSpecialityLyt=findViewById(R.id.selectSpecialityLyt);
        familyScroll=findViewById(R.id.familyScroll);
        img_edit_icon=findViewById(R.id.img_edit_icon);
        imgDownArrow=findViewById(R.id.imgDownArrow);
        imgUpArrow=findViewById(R.id.imgUpArrow);
        imgRemove=findViewById(R.id.imgRemove);
        tvFamilyName=findViewById(R.id.tvFamilyName);
        tvFamilyAge=findViewById(R.id.tvFamilyAge);
        tvFamilyHeight=findViewById(R.id.tvFamilyHeight);
        tvFamilyWeight=findViewById(R.id.tvFamilyWeight);
        tvSpecialityName=findViewById(R.id.tvSpecialityName);
        edtQuery=findViewById(R.id.edtQuery);
        tvFee=findViewById(R.id.tvFee);
        btnIcq7=findViewById(R.id.btnIcq7);
        dialog = new ProgressDialog(SevenDayChatActivity.this);
        img_edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFamilyDetails();
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Log_Status = sharedpreferences.getString(Login_Status, "");
        selectSpecialityLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SevenDayChatActivity.this, SpecialityListActivity.class);
                startActivity(intent);
            }
        });
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.select_specname = "";
                Model.select_spec_val = "0";
                tvSpecialityName.setText("Select Speciality (optional)");
                imgRemove.setVisibility(View.GONE);
            }
        });
        btnIcq7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new NetCheck().netcheck(SevenDayChatActivity.this)) {

                    Model.query_cache = edtQuery.getText().toString();

                    if (Log_Status.equals("1")) {

                        if (Model.id != null && !Model.id.isEmpty() && !Model.id.equals("null")) {
                            try {
                                String query_str = edtQuery.getText().toString();

                                if ((query_str.length()) > 0) {
                                    String spinText = tvSpecialityName.getText().toString();
                                    spec_val = Model.select_spec_val;
                                    query_txt = URLEncoder.encode((edtQuery.getText().toString()), "UTF-8");
                                    JsonObject json = new JsonObject();
                                    json.addProperty("query", (edtQuery.getText().toString()));

                                    if (spinText.equals("Choose speciality (optional)"))
                                    {
                                        spec_val = "0";
                                    }
                                    if (spinText.length() <= 0)
                                    {
                                        spec_val = "0";
                                    }
                                    json.addProperty("is_icq100", "1");
                                    json.addProperty("speciality", spec_val);
                                    json.addProperty("doctor_id", "0");
                                    json.addProperty("pqid", "0");
                                    json.addProperty("qid", "0");
                                    json.addProperty("fp_id", radio_id);
                                    postChatQuery(json);
                                } else
                                    edtQuery.setError("Please enter your query");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Intent intent=new Intent(SevenDayChatActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent=new Intent(SevenDayChatActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(SevenDayChatActivity.this, "Please check your Internet Connection and try again", Toast.LENGTH_LONG).show();
                }
            }
        });
        getFamilyList();
        getFeesForICQ7();
    }

    private void getFeesForICQ7() {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", Model.id);
            jsonObject.addProperty("doctor_id", "0");
            jsonObject.addProperty("item_type", "icq100");
            jsonObject.addProperty("all", "0");

        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Model.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getFeesForICQ7Days(Model.token,jsonObject).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                dialog.dismiss();
                JSONObject object;
                try {
                     object=new JSONObject(response.body().toString());
                     Log.e("object",object.toString()+" ");
                    if (object.has("str_fee")) {
                        strFeesTxt = object.getString("str_fee");
                        tvFee.setText("(" + strFeesTxt + ")");
                    } else {
                        tvFee.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
            }
        });

    }


    private void postChatQuery(JsonObject json) {
        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(false);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Model.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RetrofitService service = retrofit.create(RetrofitService.class);

        Utils.generateBaseUrl().create(RetrofitService.class).postQueryDetails(Model.id,  Model.token,json).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response.code() == 200) {
                    try {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (object.has("token_status")) {
                            String token_status = object.getString("token_status");
                            if (token_status.equals("0")) {

                                finishAffinity();
                                Intent intent = new Intent(SevenDayChatActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            qid = object.getString("qid");
                            Log.e("object",object.toString()+" ");
                            if (object.has("persona_id")) {
                                personaIdVal = object.getString("persona_id");
                            } else {
                                personaIdVal = "0";
                            }
                            if (qid != null && !qid.isEmpty() && !qid.equals("null") && !qid.equals("")) {

                                if (object.has("icq100_id")) {

                                    icq100IdVal = object.getString("icq100_id");

                                     prepareInvoice();
                                }
                            } else {
                                Toast.makeText(SevenDayChatActivity.this, "Something went wrong. Please go back and try again..!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SevenDayChatActivity.this, CenterFabActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(SevenDayChatActivity.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
             t.printStackTrace();
                 dialog.dismiss();
             Toast.makeText(SevenDayChatActivity.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void prepareInvoice() {
        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(false);
        RetrofitService service=Utils.generateBaseUrl().create(RetrofitService.class);
        Log.e("service", service.getPrepareInvoice(Model.id, infFor, icq100IdVal,"android",Model.token,"1").request().url()+" ");
        service.getPrepareInvoice(Model.id, infFor, icq100IdVal,"android",Model.token,"1").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("response",response.body()+" ");
                if (response.code()==200){
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().toString());
                        if (jsonObject.has("token_status")) {
                            String token_status = jsonObject.getString("token_status");
                            if (token_status.equals("0")) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Login_Status, "0");
                                editor.apply();

                                finishAffinity();
                                Intent intent = new Intent(SevenDayChatActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            dialog.dismiss();
                            invId = jsonObject.getString("id");
                            invFee = jsonObject.getString("fee");
                            invStrFee = jsonObject.getString("str_fee");
                            Log.e("prepareInv in 1", invId +" ");

                            if (!(invId).equals("0")) {

                                Model.have_free_credit = "0";

                                Model.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
                                Bundle params = new Bundle();
                                params.putString("user_id", Model.id);
                                Model.mFirebaseAnalytics.logEvent("icq100hrs_Prepare_Invoice", params);


                                Map<String, String> articleParams = new HashMap<String, String>();
                                articleParams.put("Query_id:", qid);
                                articleParams.put("Invoice_id:", invId);
                                articleParams.put("Invoice_fee:", invStrFee);
                                FlurryAgent.logEvent("android.patient.Query_Submit_Success", articleParams);

                                Intent intent = new Intent(SevenDayChatActivity.this, Invoice_Page_New.class);
                                intent.putExtra("qid", qid);
                                intent.putExtra("inv_id", invId);
                                intent.putExtra("inv_strfee", invStrFee);
                                intent.putExtra("type", "icq100");
                                startActivity(intent);
                                finish();

                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }

                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
             t.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    private void getFamilyList() {
        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Model.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);

    service.getFamily(Model.id,  Model.token).enqueue(new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

            try {
                dialog.dismiss();
                parentLayout.removeAllViews();
                relationTypeVal = "";

                JSONArray jsonArray = new JSONArray(response.body().toString());

                RadioGroup ll = new RadioGroup(SevenDayChatActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);


                if (response.body().toString().length() > 5) {

                    for (int i = -1; i < (jsonArray.length()) + 1; i++) {

                        RadioButton radioButton = new RadioButton(SevenDayChatActivity.this);
                        radioButton.setId(((jsonArray.length()) * 2) + i);
                        radioButton.setButtonDrawable(null);
                        radioButton.setTextColor(getResources().getColor(R.color.grey_800));
                        radioButton.setTextSize(14);
                        radioButton.setGravity(Gravity.CENTER);

                        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10, 10, 10, 10);
                        radioButton.setLayoutParams(params);

                        if (i == -1) {
                            Drawable drawable = getResources().getDrawable(R.drawable.someone_button);
                            drawable = new ScaleDrawable(drawable, 0, 10, 10).getDrawable();
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                            radioButton.setText("Myself");
                         } else if (i == jsonArray.length()) {
                            Drawable drawable = getResources().getDrawable(R.drawable.someone_else_button);
                            drawable = new ScaleDrawable(drawable, 0, 10, 10).getDrawable();
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                            radioButton.setText("Someone else");
                        } else {
                            JSONObject jsonobj1 = jsonArray.getJSONObject(i);

                            String id_val = jsonobj1.getString("id");
                            relationTypeVal = jsonobj1.getString("relation_type");
                            String name_val = jsonobj1.getString("name");
                            String gender_val = jsonobj1.getString("gender");

                            if (relationTypeVal.equals("Myself")) {
                                myself_id = id_val;
                                System.out.println("Inner myself_id---- :" + myself_id);
                            } else {
                                System.out.println("Else myself_id---- :" + myself_id);
                            }

                            family_map.put(name_val, id_val);

                            Drawable drawable = getResources().getDrawable(R.drawable.someone_button);
                            drawable = new ScaleDrawable(drawable, 0, 10, 10).getDrawable();
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);

                            radioButton.setText(name_val);
                            radioButton.setMaxEms(5);
                            radioButton.setEllipsize(TextUtils.TruncateAt.END);
                            radioButton.setMaxLines(1);
                        }
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RadioButton r1 = v.findViewById(v.getId());
                                r1.setSelected(true);
                                String rad_name = r1.getText().toString();
                                radio_id = family_map.get(rad_name);
                                switch (rad_name) {
                                    case "Someone else": {
                                        Intent intent = new Intent(SevenDayChatActivity.this, SomeoneEdit_Dialog.class);
                                        intent.putExtra("add_type", "someone");
                                        intent.putExtra("profile_id", "0");
                                        startActivity(intent);
                                        radio_id = "0";
                                        break;
                                    }
                                    case "Myself": {

                                        if (myself_id != null && !myself_id.isEmpty() && !myself_id.equals("null") && !myself_id.equals("")) {
                                            radio_id = myself_id;
                                       } else {
                                            Intent intent = new Intent(SevenDayChatActivity.this, SomeoneEdit_Dialog.class);
                                            intent.putExtra("add_type", "myself");
                                            intent.putExtra("profile_id", "0");
                                            startActivity(intent);
                                        }

                                        break;
                                    }
                                }
                                if (radio_id != null && !radio_id.equals("0")) {
                                    getFamilyDetails();
                                }
                            }
                        });
                        if (!relationTypeVal.equals("Myself")) {
                            ll.addView(radioButton);
                        } else {
                             relationTypeVal = "";
                        }
                    }

                } else {

                    for (int i = 0; i < 2; i++) {

                        RadioButton radioButton = new RadioButton(SevenDayChatActivity.this);
                        radioButton.setId(((jsonArray.length()) * 2) + i);
                        radioButton.setButtonDrawable(null);
                        radioButton.setTextColor(getResources().getColor(R.color.grey_800));
                        radioButton.setTextSize(14);
                        radioButton.setGravity(Gravity.CENTER);

                        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10, 10, 10, 10);
                        radioButton.setLayoutParams(params);
                        if (i == 0) {
                            radioButton.setText("Myself");
                            Drawable drawable = getResources().getDrawable(R.drawable.someone_button);
                            drawable = new ScaleDrawable(drawable, 0, 10, 10).getDrawable();
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                        }
                        if (i == 1) {
                            radioButton.setText("Someone else");
                            Drawable drawable = getResources().getDrawable(R.drawable.someone_else_button);
                            drawable = new ScaleDrawable(drawable, 0, 10, 10).getDrawable();
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                        }


                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RadioButton r1 = v.findViewById(v.getId());
                                String rad_name = r1.getText().toString();
                                switch (rad_name) {
                                    case "Someone else": {
                                        Intent intent = new Intent(SevenDayChatActivity.this, SomeoneEdit_Dialog.class);
                                        intent.putExtra("add_type", "someone");
                                        startActivity(intent);
                                        break;
                                    }
                                    case "Myself": {
                                        Intent intent = new Intent(SevenDayChatActivity.this, SomeoneEdit_Dialog.class);
                                        intent.putExtra("add_type", "myself");
                                        startActivity(intent);

                                        break;
                                    }
                                }
                                if (!radio_id.equals("0")) {
                                  getFamilyDetails();
                                }

                            }
                        });


                        ll.addView(radioButton);
                    }

                }
                parentLayout.addView(ll);
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();
            }

        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            t.printStackTrace();
            dialog.dismiss();
        }
    });
    }

    private void getFamilyDetails() {
        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Model.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getFamilyDetails(Model.id,radio_id,"1",Model.token).enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    dialog.dismiss();

                    JSONObject fam_obj = new JSONObject(response.body().toString());

                    String name_text = fam_obj.getString("name");
                    String relation_type_text = fam_obj.getString("relation_type");
                    String age_text = fam_obj.getString("age");
                    String gender_text = fam_obj.getString("gender");
                    String height_text = fam_obj.getString("height");
                    String weight_text = fam_obj.getString("weight");

                    if (!relation_type_text.isEmpty() && !relation_type_text.equals("null")) {
                    } else {
                        relation_type_text = "";
                    }
                    if (!age_text.isEmpty() && !age_text.equals("null")) {
                    } else {
                        age_text = "";

                    }
                    if (!gender_text.isEmpty() && !gender_text.equals("null")) {
                    } else {
                        gender_text = "";
                    }
                    if (!height_text.isEmpty() && !height_text.equals("null")) {
                    } else {
                        height_text = "";
                    }
                    if (!weight_text.isEmpty() && !weight_text.equals("null")) {
                    } else {
                        weight_text = "";
                    }
                    if (!name_text.isEmpty() && !name_text.equals("null")) {
                    } else {
                        name_text = "";
                    }


                    tvFamilyName.setText(name_text);
                    tvFamilyAge.setText(relation_type_text + " - " + gender_text);
                    tvFamilyHeight.setText("Height : " + height_text);
                    tvFamilyWeight.setText("Weight : " + weight_text);


                    Log.e("radio_id=============",  radio_id+" ");
                    if (gender_text.isEmpty()) {
                        editFamilyDetails();
                    }

                    if (!radio_id.equals("0")) {
                        detailsLayout.setVisibility(View.VISIBLE);
                        imgDownArrow.setVisibility(View.GONE);
                        imgUpArrow.setVisibility(View.VISIBLE);
                    } else {
                        detailsLayout.setVisibility(View.GONE);
                        imgDownArrow.setVisibility(View.VISIBLE);
                        imgUpArrow.setVisibility(View.GONE);

                    }

                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            t.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    private void editFamilyDetails() {
        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Model.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getFamilyDetails(Model.id,radio_id,"0",Model.token).enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    dialog.dismiss();
                   JSONObject fam_obj = new JSONObject(response.body().toString());

                    String rel_val = fam_obj.getString("relation_type");
                    String tit_id = fam_obj.getString("title_id");
                    String mem_name = fam_obj.getString("name");
                    String  age_val = fam_obj.getString("age");
                    String  dob_val = fam_obj.getString("dob");
                    String gender_val = fam_obj.getString("gender");
                    String  height_val = fam_obj.getString("height");
                    String  weight_val = fam_obj.getString("weight");

                    System.out.println("tit_id-1--------------" + tit_id);
                    System.out.println("mem_name 1---------------" + mem_name);
                    System.out.println("rel_type 1---------------" + rel_val);
                    System.out.println("age_val 1---------------" + age_val);
                    System.out.println("dob_val 1---------------" + dob_val);
                    System.out.println("gender_val- 1--------------" + gender_val);
                    System.out.println("height_val-1--------------" + height_val);
                    System.out.println("weight_val---------------" + weight_val);

                    Intent intent = new Intent(SevenDayChatActivity.this, SomeoneEdit_Dialog.class);
                    intent.putExtra("add_type", "edit");
                    intent.putExtra("profile_id", radio_id);
                    intent.putExtra("tit_id", tit_id);
                    intent.putExtra("mem_name", mem_name);
                    intent.putExtra("rel_val", rel_val);
                    intent.putExtra("age_val", age_val);
                    intent.putExtra("dob_val", dob_val);
                    intent.putExtra("gender_val", gender_val);
                    intent.putExtra("height_val", height_val);
                    intent.putExtra("weight_val", weight_val);
                    startActivity(intent);

                    Log.e("Get name_text",  mem_name);
                    Log.e("Get title_id" , tit_id);
                    Log.e("Get dob_text" , dob_val);
                    Log.e("Get relation_type_text" , rel_val);
                    Log.e("Get age_text" , age_val);
                    Log.e("gender_text" , gender_val);
                    Log.e("height_text" , height_val);
                    Log.e("weight_text" , weight_val);

                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        detailsLayout.setVisibility(View.GONE);
//        if ((Model.select_specname) != null && !(Model.select_specname).isEmpty() && !(Model.select_specname).equals("null") && !(Model.select_specname).equals("")) {
//            tvSpecialityName.setText(Model.select_specname);
//            imgRemove.setVisibility(View.VISIBLE);
//        }

//        try {
            if ((Model.query_launch).equals("SpecialityListActivity")) {

                if ((Model.select_spec_val).equals("0")) {
                    Model.select_specname = "";
                    Model.select_spec_val = "0";
                    tvSpecialityName.setText("Select Speciality (optional)");
                    imgRemove.setVisibility(View.GONE);
                } else {
                    if ((Model.select_specname) != null && !(Model.select_specname).isEmpty() && !(Model.select_specname).equals("null") && !(Model.select_specname).equals("")) {
                        tvSpecialityName.setText(Model.select_specname);
                        imgRemove.setVisibility(View.VISIBLE);
                    }
                }
            } else if ((Model.query_launch).equals("SomeOneEdit")) {
                detailsLayout.setVisibility(View.GONE);
                Model.query_launch = "";
                getFamilyList();
            }

    }
}