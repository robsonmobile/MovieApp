package app.com.rio.movieapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import app.com.rio.movieapp.R;
import app.com.rio.movieapp.activity.DetailActivity;
import app.com.rio.movieapp.adapter.ArchivedMovieAdapter;
import app.com.rio.movieapp.data.RealmHelper;
import app.com.rio.movieapp.model.ArchivedMovies;
import app.com.rio.movieapp.model.Movies;
import app.com.rio.movieapp.util.ItemClickSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rio on 07/05/16.
 */
public class FavoriteFragment extends BaseFragment {

    private static final String OBJECT_SORT = "favorite";

    private ArchivedMovieAdapter mAdapter;
    private ArrayList<ArchivedMovies> mListMovieResult = new ArrayList<>();
    private RealmHelper mRealmHelper;

    @Bind(R.id.rv_movie_fragment)
    RecyclerView mRecyclerView;
    @Bind(R.id.ll_error)
    LinearLayout mLayoutError;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_fragment_fav, container, false);
        ButterKnife.bind(this, rootView);

        mRealmHelper = new RealmHelper(getActivity());

        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mListMovieResult = mRealmHelper.findAllArchivedMovies();

        mAdapter = new ArchivedMovieAdapter(getActivity(), mListMovieResult);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ArchivedMovies archivedMovies = mListMovieResult.get(position);

                Movies movies = new Movies();

                movies.id = archivedMovies.id;
                movies.poster_path = archivedMovies.poster_path;
                movies.overview = archivedMovies.overview;
                movies.release_date = archivedMovies.release_date;
                movies.original_title = archivedMovies.original_title;
                movies.original_language = archivedMovies.original_language;
                movies.title = archivedMovies.title;
                movies.backdrop_path = archivedMovies.backdrop_path;
                movies.popularity = archivedMovies.popularity;
                movies.vote_count = archivedMovies.vote_count;
                movies.vote_average = archivedMovies.vote_average;
                movies.type_sort = archivedMovies.type_sort;

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("movies", movies);
                startActivity(intent);
            }
        });

        if (mAdapter.getItemCount() <= 0) {
            mLayoutError.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            mLayoutError.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

    }

}
