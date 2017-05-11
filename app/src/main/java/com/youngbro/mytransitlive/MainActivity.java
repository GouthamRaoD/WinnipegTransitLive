package com.youngbro.mytransitlive;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private SlidingTabLayout mtab;
    private ViewPager mpage;
    MyAdapter myAdapter;
    String tab[] ={"NearBy Stops","Favorites","Service News","Map"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mpage = (ViewPager) findViewById(R.id.pages);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        mpage.setAdapter(myAdapter);
        mtab = (SlidingTabLayout) findViewById(R.id.tabs);
        mtab.setDistributeEvenly(true);
        mtab.setCustomTabView(R.layout.custom_tab,R.id.tabText);
        mtab.setViewPager(mpage);
        mpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                toolbar.setTitle(tab[position]);
                setSupportActionBar(toolbar);
                invalidateOptionsMenu();
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3)
                {
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(mpage.getCurrentItem() == 0)
            getMenuInflater().inflate(R.menu.main, menu);
        else
            getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
            return true;
        }
        else if(id == R.id.action_settings){
            Intent intent = new Intent(MainActivity.this, Search.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class MyAdapter extends FragmentPagerAdapter{
        int icons[]={R.drawable.location,R.drawable.favorite1,R.drawable.news,R.drawable.map};
        String tabs[] = {"Near", "Favour","News","Map"};
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new NearByStops();
                case 1:
                    return new Favourite();
                case 2:
                    return new ServiceNews();
                case 3:
                    return new NearByMap();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable= ResourcesCompat.getDrawable(getResources(), icons[position], null);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
