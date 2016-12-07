package com.example.jihwan.demolistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        data = new ArrayList<>();
        data.add("월");
        data.add("화");
        data.add("수");
        data.add("목");
        data.add("금");
        data.add("토");
        data.add("일");
        data.add("월2");
        data.add("화2");
        data.add("수2");
        data.add("목2");
        data.add("금2");
        data.add("토2");
        data.add("일2");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, data);
        listView.setAdapter(adapter);

    }
}
