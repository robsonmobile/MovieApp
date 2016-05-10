package app.com.rio.movieapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import app.com.rio.movieapp.data.MovieField;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by rio on 07/05/16.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;
    private RequestQueue requestQueue;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .schemaVersion(0)
                .migration(new DataMigration())
                .build();

        Realm.setDefaultConfiguration(configuration);
    }

    public static synchronized MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }


    /**
     * call requestqueue from volley
     *
     * @return requestQueue
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(sInstance);
        return requestQueue;
    }

    /**
     * add Request to Queue, make it generic
     *
     * @param request<T>
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> request, String tag, int timeout) {
        request.setTag(tag);
        Log.d("tagging", "request URL -> " + request.getUrl());
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        getRequestQueue().add(request);
    }

    /**
     * Cancel all request from Tag
     *
     * @param tag
     */
    public void cancelPendingRequest(String tag) {
        if (requestQueue != null)
            requestQueue.cancelAll(tag);
    }

    /**
     * Clear cache Volley
     *
     */
    public void clearVolleyCache() {
        if (this.requestQueue != null) {
            this.requestQueue.getCache().clear();
        }
    }

    /**
     * Setting for Realm Migration
     */
    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

//            GET schema
            RealmSchema schema = realm.getSchema();

//            CREATE new schema if schema 0
            if (oldVersion == 0) {
                schema.create("MovieRealm")
                        .addField(MovieField.COLUMN_ID, long.class)
                        .addField(MovieField.COLUMN_POSTER_PATH, String.class)
                        .addField(MovieField.COLUMN_OVERVIEW, String.class)
                        .addField(MovieField.COLUMN_RELEASE_DATE, String.class)
                        .addField(MovieField.COLUMN_ORIGINAL_TITLE, String.class)
                        .addField(MovieField.COLUMN_ORIGINAL_LANGUAGE, String.class)
                        .addField(MovieField.COLUMN_TITLE, String.class)
                        .addField(MovieField.COLUMN_BACKDROP_PATH, String.class)
                        .addField(MovieField.COLUMN_POPULARITY, double.class)
                        .addField(MovieField.COLUMN_VOTE_COUNT, long.class)
                        .addField(MovieField.COLUMN_VOTE_AVERAGE, double.class)
                        .addField(MovieField.COLUMN_MOVIE_SORT_BY, String.class);

                schema.create("ArchivedMovieRealm")
                        .addField(MovieField.COLUMN_ID, long.class)
                        .addField(MovieField.COLUMN_POSTER_PATH, String.class)
                        .addField(MovieField.COLUMN_OVERVIEW, String.class)
                        .addField(MovieField.COLUMN_RELEASE_DATE, String.class)
                        .addField(MovieField.COLUMN_ORIGINAL_TITLE, String.class)
                        .addField(MovieField.COLUMN_ORIGINAL_LANGUAGE, String.class)
                        .addField(MovieField.COLUMN_TITLE, String.class)
                        .addField(MovieField.COLUMN_BACKDROP_PATH, String.class)
                        .addField(MovieField.COLUMN_POPULARITY, double.class)
                        .addField(MovieField.COLUMN_VOTE_COUNT, long.class)
                        .addField(MovieField.COLUMN_VOTE_AVERAGE, double.class)
                        .addField(MovieField.COLUMN_MOVIE_SORT_BY, String.class);

                oldVersion++;
            }

        }
    }
}
