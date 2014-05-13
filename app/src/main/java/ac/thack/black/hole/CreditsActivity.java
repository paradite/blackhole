package ac.thack.black.hole;

import android.os.Bundle;

import ac.thack.black.hole.R;

public class CreditsActivity extends MainActivity {
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.credits);
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