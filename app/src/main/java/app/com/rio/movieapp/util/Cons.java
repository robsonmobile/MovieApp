package app.com.rio.movieapp.util;

/**
 * Created by rio on 07/05/16.
 */
public class Cons {
    public static final String TAG = "MOVIEAPP";
    public static final String API_KEY_MOVIE = "";
    public static final String TEMP_GET_IMAGE = "http://image.tmdb.org/t/p/w185";
    public static final String TEMP_GET_IMAGE_DETAIL = "http://image.tmdb.org/t/p/w342";
    public static final String PREF_MOVIE = "pref_movie";
    public static final String INTENT_VIDEO = "video_youtube";

    public static final String YOUTUBE_KEY = "";

    /**
     * TIME_OUT VOLLEY
     */
    public static final int RUN_TIMEOUT = 30000;
    public static final int RUN_TIMEOUT_SHORT = 5000;

    /**
     * END_POINT GET MOVIE LIST
     */
    public static final String URL_GET_LIST(String object) {
        return "http://api.themoviedb.org/3/movie/" + object + "?api_key=" + Cons.API_KEY_MOVIE;
    }

    public static final String URL_GET_TRAILER(String id) {
        return "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + Cons.API_KEY_MOVIE;
    }

    public static final String URL_GET_REVIEWS(String reviews) {
        return "http://api.themoviedb.org/3/movie/" + reviews + "/reviews?api_key=" + Cons.API_KEY_MOVIE;
    }

}
