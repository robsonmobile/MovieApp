package app.com.rio.movieapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import app.com.rio.movieapp.MyApplication;
import app.com.rio.movieapp.R;
import app.com.rio.movieapp.activity.DetailActivity;
import app.com.rio.movieapp.adapter.MovieParalaxAdapter;
import app.com.rio.movieapp.data.MovieField;
import app.com.rio.movieapp.data.RealmHelper;
import app.com.rio.movieapp.model.Movies;
import app.com.rio.movieapp.util.Cons;
import app.com.rio.movieapp.util.ItemClickSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rio on 07/05/16.
 */
public class TopRatedFragment extends BaseFragment {

    private static final String LOG_TAG = TopRatedFragment.class.getSimpleName();
    private static final String LAST_UPDATE_DAY_PREF = "last_update_pref_top";
    private static final String OBJECT_SORT = "top_rated";
    private SharedPreferences pref;
    private SimpleDateFormat sdf;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private MovieParalaxAdapter mAdapter;
    private ArrayList<Movies> mListMovie = new ArrayList<>();
    private ArrayList<Movies> mListMovieResult = new ArrayList<>();
    private RealmHelper mRealmHelper;

    @Bind(R.id.rv_movie_fragment)
    RecyclerView mRecyclerView;
    @Bind(R.id.ll_error)
    LinearLayout mLayoutError;

    private String todayDate;
    private boolean startApp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_fragment, container, false);
        ButterKnife.bind(this, rootView);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String lastUpdate = pref.getString(LAST_UPDATE_DAY_PREF, "");
        todayDate = sdf.format(Calendar.getInstance().getTime());

        mRealmHelper = new RealmHelper(getActivity());

        if (isOnline()) {
            if (!todayDate.equals(lastUpdate)) {
                startApp = true;
                getListMovie(OBJECT_SORT);
            }else {
                startApp = false;
            }
        }else {
            Toast.makeText(getActivity(), "Koneksi bermasalah", Toast.LENGTH_LONG).show();
        }

        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mListMovieResult = mRealmHelper.findAllMovies(OBJECT_SORT);

        if (startApp) {
            mAdapter = new MovieParalaxAdapter(getActivity(), mListMovie);
        }else {
            mAdapter = new MovieParalaxAdapter(getActivity(), mListMovieResult);
        }

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (startApp) {
                    Movies movies = mListMovie.get(position);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("movies", movies);
                    startActivity(intent);
                }else {
                    Movies movies = mListMovieResult.get(position);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("movies", movies);
                    startActivity(intent);
                }
            }
        });

    }

    private void getListMovie(final String objectSort) {
        String url = Cons.URL_GET_LIST(objectSort);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Movies movies = new Movies();

                        movies.id = jsonObject.getLong(MovieField.COLUMN_ID);
                        movies.poster_path = jsonObject.getString(MovieField.COLUMN_POSTER_PATH);
                        movies.overview = jsonObject.getString(MovieField.COLUMN_OVERVIEW);
                        movies.release_date = jsonObject.getString(MovieField.COLUMN_RELEASE_DATE);
                        movies.original_title = jsonObject.getString(MovieField.COLUMN_ORIGINAL_TITLE);
                        movies.original_language = jsonObject.getString(MovieField.COLUMN_ORIGINAL_LANGUAGE);
                        movies.title = jsonObject.getString(MovieField.COLUMN_TITLE);
                        movies.backdrop_path = jsonObject.getString(MovieField.COLUMN_BACKDROP_PATH);
                        movies.vote_average = jsonObject.getDouble(MovieField.COLUMN_VOTE_AVERAGE);
                        movies.vote_count = jsonObject.getLong(MovieField.COLUMN_VOTE_COUNT);
                        movies.popularity = jsonObject.getDouble(MovieField.COLUMN_POPULARITY);

                        movies.type_sort = objectSort;

                        mListMovie.add(movies);

                    }

                    if (mListMovie.size() > 0) {
                        mRealmHelper.bulkInsertMovie(mListMovie);
                    }

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(LAST_UPDATE_DAY_PREF, todayDate);
                    editor.commit();

                    mAdapter.notifyDataSetChanged();

                    if (mAdapter.getItemCount() <= 0) {
                        mLayoutError.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }else {
                        mLayoutError.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("GET TOP RATED LIST", "Error" + error.getMessage());
            }
        });

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest, LOG_TAG, Cons.RUN_TIMEOUT_SHORT);
    }

}
