package ac.thack.black.hole;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import ac.thack.black.hole.R;

public class HowtoActivity extends MainActivity {
    private static final String TAG = HowtoActivity.class.getSimpleName();
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.howto);

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
