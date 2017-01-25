package com.example.dev.mistakeerror;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.dev.mistakeerror.adapter.ViewPagerAdapter;

public class StartActivity extends AppCompatActivity {


    private ViewPagerAdapter adapter;
    private ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setAdapter(adapter);


    }
}
