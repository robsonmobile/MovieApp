package app.com.rio.movieapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.rio.movieapp.util.Cons;

/**
 * Created by rio on 09/05/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(Cons.PREF_MOVIE, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSharedPreferences = getSharedPreferences(Cons.PREF_MOVIE, MODE_PRIVATE);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * This check connection
     */
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
