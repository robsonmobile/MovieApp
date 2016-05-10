package app.com.rio.movieapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.rio.movieapp.MyApplication;
import app.com.rio.movieapp.R;
import app.com.rio.movieapp.adapter.ReviewExpandAdapter;
import app.com.rio.movieapp.adapter.TrailerExpandAdapter;
import app.com.rio.movieapp.data.RealmHelper;
import app.com.rio.movieapp.model.ArchivedMovies;
import app.com.rio.movieapp.model.Movies;
import app.com.rio.movieapp.model.Review;
import app.com.rio.movieapp.model.Trailer;
import app.com.rio.movieapp.util.Cons;
import app.com.rio.movieapp.util.ExpandableHeightListView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.rl_content_cover)
    RelativeLayout mLayoutCover;
    @Bind(R.id.iv_movie_detail)
    ImageView ivCover;
    @Bind(R.id.tv_title_movie_detail)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.content_detail_movie)
    RelativeLayout mLayoutContent;
    @Bind(R.id.tv_overview)
    TextView tvOverview;
    @Bind(R.id.rl_list_trailer)
    LinearLayout mLayoutTrailer;
    @Bind(R.id.rl_list_review)
    LinearLayout mLayoutReview;
    @Bind(R.id.pb_loading)
    ProgressBar pbTrailer;
    @Bind(R.id.tv_loading)
    TextView tvLoadingTrailer;
    @Bind(R.id.pb_loading_review)
    ProgressBar pbReview;
    @Bind(R.id.tv_loading_review)
    TextView tvLoadingReview;
    @Bind(R.id.pb_movie_detail)
    ProgressBar pbDetailMovie;

    private ArrayList<Trailer> mTrailerList = new ArrayList<>();
    private ArrayList<Review> mReviewList = new ArrayList<>();
    private ExpandableHeightListView mExpandTrailer;
    private ExpandableHeightListView mExpandReview;
    private TrailerExpandAdapter mTrailerAdapter;
    private ReviewExpandAdapter mReviewAdapter;
    private Movies movies;
    private RealmHelper mRealmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mRealmHelper = new RealmHelper(DetailActivity.this);

        movies = getIntent().getParcelableExtra("movies");

        setupToolbar();
        setupDisplay();

        getListTrailer(String.valueOf(movies.id));
        getListReview(String.valueOf(movies.id));

    }

    private void setupDisplay() {
        Glide.with(DetailActivity.this)
                .load(Cons.TEMP_GET_IMAGE_DETAIL + movies.backdrop_path)
                .error(R.drawable.error_image)
                .into(ivCover);

        tvTitle.setText(movies.title);
        tvOverview.setText(movies.overview);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            setResult(RESULT_OK);
            finish();
        }

        if (id == R.id.mn_star) {

            showDialogAddFavorite();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getListTrailer(final String id) {
        String url = Cons.URL_GET_TRAILER(id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Trailer trailer = new Trailer();

                        trailer.id = jsonObject.getString("id");
                        trailer.name = jsonObject.getString("name");
                        trailer.key = jsonObject.getString("key");
                        trailer.site = jsonObject.getString("site");
                        trailer.type = jsonObject.getString("type");

                        mTrailerList.add(trailer);

                    }

                    showListTrailer(mTrailerList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("GET TRAILER", "Error" + error.getMessage());
            }
        });

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest, "Trailer", Cons.RUN_TIMEOUT_SHORT);
    }

    private void getListReview(final String id) {
        String url = Cons.URL_GET_REVIEWS(id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Review review = new Review();

                        review.id = jsonObject.getString("id");
                        review.author = jsonObject.getString("author");
                        review.content = jsonObject.getString("content");
                        review.url = jsonObject.getString("url");

                        mReviewList.add(review);

                    }

                    showListReview(mReviewList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("GET REVIEW", "Error" + error.getMessage());
            }
        });

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest, "Review", Cons.RUN_TIMEOUT_SHORT);
    }

    private void showListTrailer(ArrayList<Trailer> list) {
        if (list.size() == 0) {
            TextView textView = new TextView(DetailActivity.this);
            textView.setGravity(Gravity.CENTER);

            textView.setText(getString(R.string.text_no_trailer));

            mLayoutTrailer.addView(textView);
        }else {
            mExpandTrailer = new ExpandableHeightListView(this);
            mTrailerAdapter = new TrailerExpandAdapter(this, Glide.with(this));

            mTrailerAdapter.setData(list);

            mExpandTrailer.setAdapter(mTrailerAdapter);
            mExpandTrailer.setExpanded(true);
            mExpandTrailer.setDivider(null);

            mExpandTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Trailer trailer = mTrailerList.get(position);

                    Intent intent = new Intent(DetailActivity.this, YoutubeVideoActivity.class);
                    intent.putExtra(Cons.INTENT_VIDEO, trailer.key);
                    startActivity(intent);
                }
            });

            mLayoutTrailer.removeAllViews();
            mLayoutTrailer.addView(mExpandTrailer);
            mTrailerAdapter.notifyDataSetChanged();
        }
    }

    private void showListReview(ArrayList<Review> list) {
        if (list.size() == 0) {
            TextView textView = new TextView(DetailActivity.this);
            textView.setGravity(Gravity.CENTER);

            textView.setText(getString(R.string.text_no_trailer));

            mLayoutReview.addView(textView);
        }else {
            mExpandReview = new ExpandableHeightListView(this);
            mReviewAdapter = new ReviewExpandAdapter(this, Glide.with(this));

            mReviewAdapter.setData(list);

            mExpandReview.setAdapter(mReviewAdapter);
            mExpandReview.setExpanded(true);
            mExpandReview.setDivider(null);

            mExpandReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Review review = mReviewList.get(position);

                    Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                    intent.putExtra("review", review);
                    startActivity(intent);
                }
            });

            mLayoutReview.removeAllViews();
            mLayoutReview.addView(mExpandReview);
            mReviewAdapter.notifyDataSetChanged();
        }
    }


    /**
     * OnClick button add favorite
     */
    public void bookmarkMovie(View view) {
        showDialogAddFavorite();
    }

    private void showDialogAddFavorite() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setMessage("Tambah ke list favorite?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArchivedMovies archivedMovies = new ArchivedMovies();

                        archivedMovies.id = movies.id;
                        archivedMovies.poster_path = movies.poster_path;
                        archivedMovies.overview = movies.overview;
                        archivedMovies.release_date = movies.release_date;
                        archivedMovies.original_title = movies.original_title;
                        archivedMovies.original_language = movies.original_language;
                        archivedMovies.title = movies.title;
                        archivedMovies.backdrop_path = movies.backdrop_path;
                        archivedMovies.popularity = movies.popularity;
                        archivedMovies.vote_count = movies.vote_count;
                        archivedMovies.vote_average = movies.vote_average;
                        archivedMovies.type_sort = movies.type_sort;

                        mRealmHelper.addArchivedMovie(archivedMovies);

                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
