package in.gajerait.liveena.Youtube;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import in.gajerait.liveena.R;

public abstract class YouTubeFailureRecoveryActivity extends YouTubeBaseActivity implements OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    protected abstract Provider getYouTubePlayerProvider();
    public static final String DEVELOPER_KEY = "AIzaSyDWR19PXnVeJcp1deL1EvRHl9wdyJ0bI3c";


    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
            return;
        }
        String string = getString(R.string.error_player);
        Object[] objArr = new Object[RECOVERY_DIALOG_REQUEST];
        objArr[0] = errorReason.toString();
        Toast.makeText(this, String.format(string, objArr), RECOVERY_DIALOG_REQUEST).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            getYouTubePlayerProvider().initialize("AIzaSyDWR19PXnVeJcp1deL1EvRHl9wdyJ0bI3c", this);
        }
    }
}
