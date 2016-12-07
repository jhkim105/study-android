package com.example.jihwan.demoactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Log.d("Demo", "SubActivity onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Demo", "SubActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Demo", "SubActivity onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Demo", "SubActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Demo", "SubActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Demo", "SubActivity onDestroy");
    }
}
