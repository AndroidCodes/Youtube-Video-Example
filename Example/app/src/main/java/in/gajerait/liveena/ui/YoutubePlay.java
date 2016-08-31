package in.gajerait.liveena.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;

import in.gajerait.liveena.R;
import in.gajerait.liveena.Youtube.YouTubeFailureRecoveryActivity;
import in.gajerait.liveena.utils.StaticDataUtility;

//import android.widget.TextView;
//import com.bhujmandir.helper.DeveloperKey;
//import com.bhujmandir.json.JsonConstant;
//import com.bhujmandir.shreesoftech.activity.R;

public class YoutubePlay extends YouTubeFailureRecoveryActivity implements OnFullscreenListener {
    String id;
    private TextView list_title;
    String name;
    public static final String DEVELOPER_KEY = StaticDataUtility.YOU_TUBE_KEY;


    public static final class VideoFragment extends YouTubePlayerFragment implements OnInitializedListener {
        private YouTubePlayer player;
        private String videoId;

        public static VideoFragment newInstance() {
            return new VideoFragment();
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initialize(DEVELOPER_KEY, this);
        }

        public void onDestroy() {
            if (this.player != null) {
                this.player.release();
            }
            super.onDestroy();
        }

        public void setVideoId(String videoId) {
            if (videoId != null && !videoId.equals(this.videoId)) {
                this.videoId = videoId;
                if (this.player != null) {
                    this.player.loadVideo(videoId);
                }
            }
        }

        public void pause() {
            if (this.player != null) {
                this.player.pause();
            }
        }

        public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean restored) {
            this.player = player;
            player.setPlayerStyle(PlayerStyle.DEFAULT);
            player.addFullscreenControlFlag(8);
            player.setOnFullscreenListener((YoutubePlay) getActivity());
            if (!restored && this.videoId != null) {
                player.loadVideo(this.videoId);
            }
        }

        public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
            this.player = null;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
      
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);
        this.list_title = (TextView) findViewById(R.id.list_title);
        Bundle b = getIntent().getExtras();
        this.id = (String) b.get("ID");
        System.out.println("Id1..."+id);
        this.name = (String) b.get("name");
        this.list_title.setText(this.name);
//        this.list_title.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "SAMARN.TTF"));
        init();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        ((VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container)).setVideoId(this.id);
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStyle(PlayerStyle.DEFAULT);
        if (!wasRestored) {
            player.loadVideo(this.id);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
    }

    public void onFullscreen(boolean arg0) {
    }
}
