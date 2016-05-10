package app.com.rio.movieapp.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import app.com.rio.movieapp.model.ArchivedMovieRealm;
import app.com.rio.movieapp.model.ArchivedMovies;
import app.com.rio.movieapp.model.MovieRealm;
import app.com.rio.movieapp.model.Movies;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rio on 10/05/16.
 */
public class RealmHelper {
    private static final String TAG = "RealmHelper";


    private Realm mRealm;
    private RealmResults<MovieRealm> mRealmResult;
    private RealmResults<ArchivedMovieRealm> mRealmResultArchived;
    private Context mContext;

    public RealmHelper(Context context) {
        mRealm = Realm.getInstance(context);
        this.mContext = context;
    }

    /**
     * Insert Movie Data with bulk
     */
    public void bulkInsertMovie(ArrayList<Movies> list) {
        MovieRealm item = new MovieRealm();

        mRealm.beginTransaction();

        for (Movies movies : list ) {

            item.setId(movies.id);
            item.setPoster_path(movies.poster_path);
            item.setOverview(movies.overview);
            item.setRelease_date(movies.release_date);
            item.setOriginal_title(movies.original_title);
            item.setOriginal_language(movies.original_language);
            item.setTitle(movies.title);
            item.setBackdrop_path(movies.backdrop_path);
            item.setPopularity(movies.popularity);
            item.setVote_count(movies.vote_count);
            item.setVote_average(movies.vote_average);
            item.setType_sort(movies.type_sort);

            mRealm.copyToRealmOrUpdate(item);
        }

        mRealm.commitTransaction();

    }

    /**
     * Insert Movie Data
     */
    public void addMovie(Movies movies) {
        MovieRealm item = new MovieRealm();

        item.setId(movies.id);
        item.setPoster_path(movies.poster_path);
        item.setOverview(movies.overview);
        item.setRelease_date(movies.release_date);
        item.setOriginal_title(movies.original_title);
        item.setOriginal_language(movies.original_language);
        item.setTitle(movies.title);
        item.setBackdrop_path(movies.backdrop_path);
        item.setPopularity(movies.popularity);
        item.setVote_count(movies.vote_count);
        item.setVote_average(movies.vote_average);
        item.setType_sort(movies.type_sort);


        mRealm.beginTransaction();
        mRealm.copyToRealm(item);
        mRealm.commitTransaction();

        Log.d("Added ; ",  " " + movies.id );
    }

    /**
     * GET all data movies
     */
    public ArrayList<Movies> findAllMovies(String object) {
        ArrayList<Movies> movieList = new ArrayList<>();

        mRealmResult = mRealm.where(MovieRealm.class).equalTo(MovieField.COLUMN_MOVIE_SORT_BY, object).findAll();

        if (mRealmResult.size() > 0) {
            Log.d("Size : ", " " + mRealmResult.size());

            for (int i = 0; i < mRealmResult.size(); i++) {
                Movies movies = new Movies();

                movies.id = mRealmResult.get(i).getId();
                movies.poster_path = mRealmResult.get(i).getPoster_path();
                movies.overview = mRealmResult.get(i).getOverview();
                movies.release_date = mRealmResult.get(i).getRelease_date();
                movies.original_title = mRealmResult.get(i).getOriginal_title();
                movies.original_language = mRealmResult.get(i).getOriginal_language();
                movies.title = mRealmResult.get(i).getTitle();
                movies.backdrop_path = mRealmResult.get(i).getBackdrop_path();
                movies.popularity = mRealmResult.get(i).getPopularity();
                movies.vote_count = mRealmResult.get(i).getVote_count();
                movies.vote_average = mRealmResult.get(i).getVote_average();
                movies.type_sort = mRealmResult.get(i).getType_sort();

                movieList.add(movies);
            }

        }

        return movieList;
    }

    /**
     * DELETE movies
     */
    public void deleteData(long id) {
        RealmResults<MovieRealm> dataResults = mRealm.where(MovieRealm.class).equalTo("id", id).findAll();
        mRealm.beginTransaction();
        dataResults.remove(0);
        dataResults.removeLast();
        dataResults.clear();
        mRealm.commitTransaction();

    }

    /**
     * UPDATE Movie
     */
    public void updateArticle(Movies movies) {
        mRealm.beginTransaction();

        MovieRealm item = mRealm.where(MovieRealm.class).equalTo("id", movies.id).findFirst();

        item.setId(movies.id);
        item.setPoster_path(movies.poster_path);
        item.setOverview(movies.overview);
        item.setRelease_date(movies.release_date);
        item.setOriginal_title(movies.original_title);
        item.setOriginal_language(movies.original_language);
        item.setTitle(movies.title);
        item.setBackdrop_path(movies.backdrop_path);
        item.setPopularity(movies.popularity);
        item.setVote_count(movies.vote_count);
        item.setVote_average(movies.vote_average);
        item.setType_sort(movies.type_sort);

        mRealm.commitTransaction();

        Log.d("realm", "Update");
    }

    /**
     * Insert Archived Movie Data
     */
    public void addArchivedMovie(ArchivedMovies movies) {
        ArchivedMovieRealm item = new ArchivedMovieRealm();

        item.setId(movies.id);
        item.setPoster_path(movies.poster_path);
        item.setOverview(movies.overview);
        item.setRelease_date(movies.release_date);
        item.setOriginal_title(movies.original_title);
        item.setOriginal_language(movies.original_language);
        item.setTitle(movies.title);
        item.setBackdrop_path(movies.backdrop_path);
        item.setPopularity(movies.popularity);
        item.setVote_count(movies.vote_count);
        item.setVote_average(movies.vote_average);
        item.setType_sort(movies.type_sort);


        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(item);
        mRealm.commitTransaction();

        Log.d("Added ; ",  " " + movies.id );
    }

    /**
     * GET all data archived Movies
     */
    public ArrayList<ArchivedMovies> findAllArchivedMovies() {
        ArrayList<ArchivedMovies> movieList = new ArrayList<>();

        mRealmResultArchived = mRealm.where(ArchivedMovieRealm.class).findAll();

        if (mRealmResultArchived.size() > 0) {
            Log.d("Size : ", " " + mRealmResultArchived.size());

            for (int i = 0; i < mRealmResultArchived.size(); i++) {
                ArchivedMovies movies = new ArchivedMovies();

                movies.id = mRealmResultArchived.get(i).getId();
                movies.poster_path = mRealmResultArchived.get(i).getPoster_path();
                movies.overview = mRealmResultArchived.get(i).getOverview();
                movies.release_date = mRealmResultArchived.get(i).getRelease_date();
                movies.original_title = mRealmResultArchived.get(i).getOriginal_title();
                movies.original_language = mRealmResultArchived.get(i).getOriginal_language();
                movies.title = mRealmResultArchived.get(i).getTitle();
                movies.backdrop_path = mRealmResultArchived.get(i).getBackdrop_path();
                movies.popularity = mRealmResultArchived.get(i).getPopularity();
                movies.vote_count = mRealmResultArchived.get(i).getVote_count();
                movies.vote_average = mRealmResultArchived.get(i).getVote_average();
                movies.type_sort = mRealmResultArchived.get(i).getType_sort();

                movieList.add(movies);
            }

        } else {
            Toast.makeText(mContext, " Kosong", Toast.LENGTH_SHORT).show();
        }

        return movieList;
    }

    /**
     * DELETE archived movies
     */
    public void deleteArchivedMovie(long id) {
        RealmResults<ArchivedMovieRealm> dataResults = mRealm.where(ArchivedMovieRealm.class).equalTo("id", id).findAll();
        mRealm.beginTransaction();
        dataResults.remove(0);
        dataResults.removeLast();
        dataResults.clear();
        mRealm.commitTransaction();

    }


}
