package app.com.rio.movieapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import app.com.rio.movieapp.R;
import app.com.rio.movieapp.util.Cons;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rio on 30/12/15.
 */
public class YoutubeVideoActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.view_shadow_toolbar)
    View viewShadow;

    private YouTubePlayerSupportFragment mFragment;
    private YouTubePlayer mPlayer;
    private String youtubeId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        setupToolbar();

        youtubeId = getIntent().getStringExtra(Cons.INTENT_VIDEO);

        mFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.view_player);
        mFragment.initialize(Cons.YOUTUBE_KEY, YoutubeVideoActivity.this);

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

    private void setupToolbar() {
        TextView titleToolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleToolbar.setMaxLines(1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleToolbar.setText("Trailer");

        if (buildVersion()) {
            viewShadow.setVisibility(View.GONE);
        }else {
            viewShadow.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This check build version
     */
    private boolean buildVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (youtubeId == null) {
            Toast.makeText(YoutubeVideoActivity.this, "Terjadi kesalahan, tidak bisa memuat video", Toast.LENGTH_SHORT).show();
        }else {
            if (!b) {
                mPlayer = youTubePlayer;
                mPlayer.cueVideo(youtubeId);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, 1).show();
        } else {
            String errorMessage = errorReason.toString();
            Log.e("errorYoutube", errorMessage);
        }
    }
}
