package com.orane.icliniq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class ThemeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ColorPicker cp = new ColorPicker(ThemeActivity.this, 255, 127, 123);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

    }
}