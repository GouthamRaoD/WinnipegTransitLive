package com.youngbro.mytransitlive;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private SlidingTabLayout mtab;
    private ViewPager mpage;
    MyAdapter myAdapter;
    String tab[] ={"NearBy Stops","Favorites","Service News","Maps"};
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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        AppRater.app_launched(this);
    }
    @Override
    public void onBackPressed() {
        switch(mpage.getCurrentItem())
        {
            case 0:
                finish();
                break;
            default:
                mpage.setCurrentItem(0, true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(mpage.getCurrentItem() == 0)
            getMenuInflater().inflate(R.menu.main, menu);
        else
            getMenuInflater().inflate(R.menu.main2, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint("StopNo/Street/Area");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(MainActivity.this, SearchResult.class);
                query.replace(" ", "%20");
                intent.putExtra("key",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
       /* else if(id == R.id.action_settings){
            Intent intent = new Intent(MainActivity.this, Search.class);
            startActivity(intent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    class MyAdapter extends FragmentPagerAdapter{
        int icons[]={R.drawable.location,R.drawable.favorite1,R.drawable.news,R.drawable.map};
        String tabs[] = {"Near", "Favour","News","Maps"};
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
