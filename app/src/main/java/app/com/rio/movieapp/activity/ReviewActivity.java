package app.com.rio.movieapp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import app.com.rio.movieapp.R;
import app.com.rio.movieapp.model.Review;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewActivity extends BaseActivity {

    @Bind(R.id.tv_author_review_detail)
    TextView tvAuthor;
    @Bind(R.id.tv_content_review_detail)
    TextView tvContent;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private Review mReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Review");

        mReview = getIntent().getParcelableExtra("review");

        tvAuthor.setText(mReview.author);
        tvContent.setText("\"" + mReview.content + "\"");
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
