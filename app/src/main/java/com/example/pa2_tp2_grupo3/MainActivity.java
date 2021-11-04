package com.example.pa2_tp2_grupo3;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends MenuController {

    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

    }

}