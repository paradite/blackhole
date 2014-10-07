package ac.thack.black.hole;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.games.Games;

import ac.thack.black.hole.model.Stats;
import ac.thack.black.hole.R;

public class NameActivity extends BaseGameActivity {
    public int width;
    public int height;
    public Stats stats;
    public boolean running=false;
    public MainGamePanel mg;
    public MainThread mt;
    public boolean backbuttonpressed = false;
    private static final String TAG = NameActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        running=false;
        backbuttonpressed = false;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
    }
    public void onClickNext(View v) {
        MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
        buttonPlayer.setLooping(false);
        buttonPlayer.seekTo(0);
        buttonPlayer.start();
        EditText blackholename = (EditText) findViewById(R.id.bhname);
        String name = blackholename.getText().toString();
        if (name.matches("")) {

            Toast.makeText(this, "You did not enter a name for your black hole", Toast.LENGTH_SHORT).show();
            return;

        }
        else {
            running=true;
            mg= new MainGamePanel(this, height, width, name);
            setContentView(mg);
            MainActivity.musicPlayer.setLooping(true);
            MainActivity.musicPlayer.seekTo(0);
            MainActivity.musicPlayer.start();
        }
    }
    @Override
    public void  onBackPressed(){
        if(running){
            mg.thread.setRunning(false);
            backbuttonpressed= true;
        }
        MainActivity.musicPlayer.stop();
        this.finish();

    }
    @Override
    protected void  onDestroy(){
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onDestroy();

        if(running)
        {
            MainActivity.musicPlayer.stop();
            Toast toast = Toast.makeText(this, "Your score is "+mg.getFinalScore(), Toast.LENGTH_LONG);

            for (int i=0; i < 2; i++)
            {
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }


/*		 score.setMode(null);
		 	if(rejected==false&&backbuttonpressed==false){
		 		ScoreloopManagerSingleton.get().onGamePlayEnded(score, null);

		 		for (int i=0; i < 2; i++)
		 				{
		 				toast.setGravity(Gravity.CENTER, 0, 0);
		 				toast.show();
		 				}

		 		final Intent intent = new Intent(this, LeaderboardsScreenActivity.class);

		 		// specify the leaderboard that will open by default
		 		intent.putExtra(LeaderboardsScreenActivity.LEADERBOARD, LeaderboardsScreenActivity.LEADERBOARD_LOCAL);
		 		startActivity(intent);
		}*/
/*		if(rejected==false&&backbuttonpressed==false){
			for (int i=0; i < 2; i++)
			 {
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

			 }
		}
		else{

		}*/
        }
    }
    public void onScoreSubmit(int score) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(getApiClient(), "CgkIltucl5MNEAIQBg", score);
        }
        else {
            final Activity that = this;
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(that, "You need to sign in using Google to submit high scores", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onSignInFailed() {
        // Sign in has failed. So show the user the sign-in button.
        View sign_in = findViewById(R.id.sign_in_button);
        View sign_out = findViewById(R.id.sign_out_button);
        if(sign_in != null) sign_in.setVisibility(View.VISIBLE);
        if(sign_out != null) sign_out.setVisibility(View.GONE);
    }

    @Override
    public void onSignInSucceeded() {
        // show sign-out button, hide the sign-in button
        View sign_in = findViewById(R.id.sign_in_button);
        View sign_out = findViewById(R.id.sign_out_button);
        if(sign_in != null) sign_in.setVisibility(View.GONE);
        if(sign_out != null) sign_out.setVisibility(View.VISIBLE);

        // (your code here: update UI, enable functionality that depends on sign in, etc)
    }
}