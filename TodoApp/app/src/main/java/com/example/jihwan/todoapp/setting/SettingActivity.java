package com.example.jihwan.todoapp.setting;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by jihwan on 15/02/2017.
 */

public class SettingActivity extends BaseActivity {


    @BindView((R.id.toolbar))
    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().add(R.id.flSetting, new SettingFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

