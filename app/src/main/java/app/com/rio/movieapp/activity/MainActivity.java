package app.com.rio.movieapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import app.com.rio.movieapp.R;
import app.com.rio.movieapp.adapter.ViewPagerAdapter;
import app.com.rio.movieapp.util.SlidingTabLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.vp_main)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    SlidingTabLayout mTabs;

    private CharSequence Titles[]={"Populer","Top Rated"};
    private int Numboftabs = 2;
    private ViewPagerAdapter mAdapterPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupTabLayout();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mn_bookmark) {
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabLayout() {
        mAdapterPager = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        mViewPager.setAdapter(mAdapterPager);

        mTabs.setDistributeEvenly(false);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });

        mTabs.setViewPager(mViewPager);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(Html.fromHtml("Movie<b>App</b>"));
    }



}
