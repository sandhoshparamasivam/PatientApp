//package com.orane.icliniq;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.RadioButton;
//import android.widget.Switch;
//
////import com.orane.icliniq.utils.ThemeColors;
//import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
//
//public class ThemeActivity extends AppCompatActivity {
//
//    RadioButton blackWitch,lightSwitch;
//    Button btnOther;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        final ColorPicker cp = new ColorPicker(ThemeActivity.this, 255, 127, 123);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_theme);
//        blackWitch=findViewById(R.id.blackWitch);
//        lightSwitch=findViewById(R.id.lightSwitch);
//        btnOther=findViewById(R.id.btnOther);
////        cp.enableAutoClose();
//        btnOther.setOnClickListener(v -> {
//            cp.show();
//            int red = cp.getRed();
//            int green = cp.getGreen();
//            int blue = cp.getBlue();
//
//            /* Or the android RGB Color (see the android Color class reference) */
////                selectedColorRGB = cp.getColor();
//
//
//            ThemeColors.setNewThemeColor(ThemeActivity.this, red, green, blue);
//            cp.dismiss();
//        });
////        blackWitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
////            // do something, the isChecked will be
////            // true if the switch is in the On position
////            Log.e("blackWitch",isChecked+" ");
////            if (isChecked){
////                lightSwitch.setChecked(false);
////                ThemeColors.setNewThemeColor(ThemeActivity.this, 0, 0, 0);
////
////            }
////
////        });
//
//        blackWitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                blackWitch.setChecked(true);
//                lightSwitch.setChecked(false);
//                ThemeColors.setNewThemeColor(ThemeActivity.this, 0, 0, 0);
//
//            }
//        });
//        lightSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lightSwitch.setChecked(true);
//                blackWitch.setChecked(false);
//                ThemeColors.setNewThemeColor(ThemeActivity.this, 255, 255, 255);
//
//            }
//        });
//
////        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                // do something, the isChecked will be
////                // true if the switch is in the On position
////                Log.e("lightSwitch",isChecked+" ");
////                if (isChecked){
////                    blackWitch.setChecked(false);
////                    ThemeColors.setNewThemeColor(ThemeActivity.this, 255, 255, 255);
////
////                }
////            }
////        });
//    }
//}