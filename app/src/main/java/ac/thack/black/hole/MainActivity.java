package ac.thack.black.hole;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.games.Games;

import ac.thack.black.hole.R;


public class MainActivity extends BaseGameActivity implements View.OnClickListener{
	/*private SoundPool soundPool;
	private int soundID;
	boolean loaded = false;*/
/*    static Log Log;
    Log log;*/
    private static final String TAG = "MainActivity";
    public final int REQUEST_LEADERBOARD = 123;
	public boolean howToSeen;
	public static MediaPlayer musicPlayer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        musicPlayer = MediaPlayer.create(this, R.raw.mainmenu);
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.start();
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        // set our ac.thack.black.hole.MainGamePanel as the View
        //mg= new ac.thack.black.hole.MainGamePanel(this, height, width);
        //setContentView(mg);
    }
	
	@Override
	protected void onPause() {
		musicPlayer.stop();
		super.onPause();
	    
	}
	@Override
	protected void onResume() {
		musicPlayer.start();
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		musicPlayer.stop();
		super.onDestroy();
     
	}

	@Override
	protected void onStop() {
		musicPlayer.stop();
		super.onStop();
	}

	public void onClickGame(View v) {
		MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
    	buttonPlayer.setLooping(false);
    	buttonPlayer.seekTo(0);
    	buttonPlayer.start();
    	if (!howToSeen){
    	howToSeen =true;
/*		 Intent intent = new Intent(this, HowtoActivity.class);
		 startActivity(intent);*/
        setContentView(R.layout.howto);
    	}
    	else{
    		 Intent intent = new Intent(this, NameActivity.class);
    		 startActivity(intent);
    	}
	}

    public void onClickContinue(View v){
        MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
        buttonPlayer.setLooping(false);
        buttonPlayer.seekTo(0);
        buttonPlayer.start();

        Intent intent = new Intent(this, NameActivity.class);
        startActivity(intent);
        this.finish();
    }
	
	public void onClickInfo(View v) {
		MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
    	buttonPlayer.setLooping(false);
    	buttonPlayer.seekTo(0);
    	buttonPlayer.start();
		  Intent intent = new Intent(this, InfoActivity.class);
		  startActivity(intent);
		}
	public void onClickHighscores(View v) {
		 MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
	    	buttonPlayer.setLooping(false);
	    	buttonPlayer.seekTo(0);
	    	buttonPlayer.start();
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), "CgkIltucl5MNEAIQBg"), REQUEST_LEADERBOARD);
        }
        else {
            Toast.makeText(this, "You need to sign in using Google to see the high scores", Toast.LENGTH_SHORT).show();
        }

		  /*Intent intent = new Intent(this, ac.thack.black.hole.HighscoreActivity.class);
		  startActivity(intent);*/
	    /*	 final Intent intent = new Intent(this, LeaderboardsScreenActivity.class);
	    	 intent.putExtra(LeaderboardsScreenActivity.LEADERBOARD, LeaderboardsScreenActivity.LEADERBOARD_LOCAL);
	         startActivity(intent);*/
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            // start the asynchronous sign in flow
            beginUserInitiatedSignIn();
        }
        else if (view.getId() == R.id.sign_out_button) {
            // sign out.
            signOut();

            // show sign-in button, hide the sign-out button
            if(findViewById(R.id.sign_in_button) != null) findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            if(findViewById(R.id.sign_out_button) != null) findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }
}

