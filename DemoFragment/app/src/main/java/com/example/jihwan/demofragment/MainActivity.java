package com.example.jihwan.demofragment;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AFragment.OnFragmentListener {

    private TextView tvMessage;

    private ViewPager viewPager;

    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessage = (TextView) findViewById(R.id.tvMessage);

//        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.flFrament, new BFragment())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
//
//        Bundle bundle = new Bundle();
//        bundle.putString(AFragment.ARG_MESSAGE, "AAAAA");
//        AFragment aFragment = new AFragment();
//        aFragment.setArguments(bundle);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.flFrament, aFragment).commit();

        viewPager = (ViewPager) findViewById(R.id.vpPager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new AFragment());
        fragmentAdapter.addFragment(new BFragment());
        fragmentAdapter.addFragment(new AFragment());
        fragmentAdapter.addFragment(new BFragment());

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvMessage.setText("Pos:" + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onSend(String message) {
        tvMessage.setText(message);

    }

    class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
