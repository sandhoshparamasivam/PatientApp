package com.orane.icliniq.wellness

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orane.icliniq.Model.Model
import com.orane.icliniq.R
import com.orane.icliniq.SpecialityListActivity


class WellnessView : AppCompatActivity() {
    lateinit var spinnerRating:Spinner
    lateinit var spinnerWellness:Spinner
    lateinit var specLayout:LinearLayout
    private lateinit var specText:TextView
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellness_view)
        specLayout=findViewById(R.id.spec_layout)
        spinnerRating=findViewById(R.id.spinner_rating)
        spinnerWellness=findViewById(R.id.spinner_wellness)
        specText=findViewById(R.id.spec_text)
        recyclerView=findViewById(R.id.recyclerView)

        specLayout.setOnClickListener {
            val intent = Intent(this@WellnessView, SpecialityListActivity::class.java)
            startActivity(intent)
        }
        spinnerWellness.adapter = ArrayAdapter(
                this,
                R.layout.spinner_item, R.id.text1,
                resources.getStringArray(R.array.wellness)
        )
        spinnerRating.adapter = ArrayAdapter(
                this,
                R.layout.spinner_item, R.id.text1,
                resources.getStringArray(R.array.starRating)
        )
        val adapter = MyListAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(this, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                    val intent=Intent(this@WellnessView,WellnessDetails::class.java)
                        startActivity(intent)
                    }
                }))
    }

    override fun onResume() {
        super.onResume()
        Log.e("select_specname", Model.select_specname + "   ")
        Log.e("query_launch", Model.query_launch + "   ")
        if (Model.query_launch == "SpecialityListActivity") {
            Model.query_launch = ""
            specText.text = Model.select_specname
        }
    }

}