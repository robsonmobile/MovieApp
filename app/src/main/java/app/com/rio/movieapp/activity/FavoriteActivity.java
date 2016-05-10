package app.com.rio.movieapp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import app.com.rio.movieapp.R;
import app.com.rio.movieapp.fragment.FavoriteFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteActivity extends BaseActivity {

    @Bind(R.id.container_favorite)
    FrameLayout mContainer;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private final String LOG_TAG = FavoriteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_favorite, new FavoriteFragment(), LOG_TAG)
                    .commit();
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bookmark Movie");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            setResult(RESULT_OK);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
