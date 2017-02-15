package com.example.jihwan.todoapp.setting;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.util.IntentUtils;

/**
 * Created by jihwan on 15/02/2017.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        findPreference("use_noti").setOnPreferenceChangeListener(this);

        findPreference("ex_list").setOnPreferenceChangeListener(this);
        String listVal = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("ex_list", "1");
        ListPreference listPreference = (ListPreference) findPreference("ex_list");
        int index = listPreference.findIndexOfValue(listVal);
        listPreference.setSummary(listPreference.getEntries()[index]);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d("jhkim", preference.getKey() + "::" + newValue.toString());

        if ("use_noti".equals(preference.getKey())) {
            IntentUtils.setServiceAlarm(getActivity(), (Boolean)newValue);
        } else if("ex_list".equals(preference.getKey())) {
            ListPreference listPreference = (ListPreference) findPreference("ex_list");
            int index = listPreference.findIndexOfValue((String)newValue);
            listPreference.setSummary(listPreference.getEntries()[index]);
        }
        return true;
    }
}
