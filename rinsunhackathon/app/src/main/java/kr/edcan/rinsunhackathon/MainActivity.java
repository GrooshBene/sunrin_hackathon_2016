package kr.edcan.rinsunhackathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by bene on 2016. 7. 22..
 */
public class MainActivity extends FragmentActivity {
    Fragment f = new Fragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(mPagerAdapter);


    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    f =  new MainPager();
                break;
                case 1:
                    f =  new ListPager();
                break;
            }
            return f;
        }

        @Override
        public int getCount() {
            return 2;  // 총 5개의 page를 보여줍니다.
        }

    }

}