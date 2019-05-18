package com.gsered.gse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ScratchGiftDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_gift_detail);

        getSupportActionBar().setTitle("Gift Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("id");
        Toast.makeText(this, "id : "+id, Toast.LENGTH_SHORT).show();
    }
}
