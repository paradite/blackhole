package ac.thack.black.hole;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import ac.thack.black.hole.R;

public class InfoActivity extends MainActivity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.info);
	        MediaPlayer musicPlayer = MediaPlayer.create(this, R.raw.mainmenu);
	        if(musicPlayer.isPlaying()==false){
	        	
	        	musicPlayer.setLooping(true);
	        	musicPlayer.seekTo(0);
	        	musicPlayer.start();
	        }
	    }
	 
	 public void onClickAchievements(View v) {
		 MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
	    	buttonPlayer.setLooping(false);
	    	buttonPlayer.seekTo(0);
	    	buttonPlayer.start();
		  Intent intent = new Intent(this, IntroActivity.class);
		  startActivity(intent);
		}
	 
	 
		public void onClickCredits(View v) {
			MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
	    	buttonPlayer.setLooping(false);
	    	buttonPlayer.seekTo(0);
	    	buttonPlayer.start();
			  Intent intent = new Intent(this, CreditsActivity.class);
			  startActivity(intent);
			}
		public void onClickHowto(View v) {
			MediaPlayer buttonPlayer = MediaPlayer.create(this, R.raw.bclick);
	    	buttonPlayer.setLooping(false);
	    	buttonPlayer.seekTo(0);
	    	buttonPlayer.start();
			  Intent intent = new Intent(this, InstructionsActivity.class);
			  startActivity(intent);
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
}
