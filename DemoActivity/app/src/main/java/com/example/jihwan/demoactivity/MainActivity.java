package com.example.jihwan.demoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnSubActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Demo", "MainActivity onCreate");
        btnSubActivity = (Button)findViewById(R.id.btn_sub_activity);
        btnSubActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Demo", "MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Demo", "MainActivity onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Demo", "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Demo", "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Demo", "MainActivity onDestroy");
    }
}
