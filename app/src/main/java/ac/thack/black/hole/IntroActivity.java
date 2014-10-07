package ac.thack.black.hole;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import java.io.File;

import ac.thack.black.hole.R;


public class IntroActivity extends Activity {
	  @Override

		  public void onCreate (Bundle icicle) {
		        super.onCreate (icicle);
		        this.setContentView (R.layout.intro);

		        // You know the that view is an instance of VideoView, so cast it as such
		        VideoView v = (VideoView) findViewById (R.id.view);

		        // This is the name of the video WITHOUT the file extension.
		        // In this example, the name of the video is 'test.mp4'
		        String videoName = "introforgame";
		        // You build the URI to your video here
		        StringBuilder uriPathBuilder = new StringBuilder ();
		        uriPathBuilder.append ("android.resource://");
		        uriPathBuilder.append (this.getPackageName ());
		        uriPathBuilder.append (File.separator);
		        uriPathBuilder.append ("raw");
		        uriPathBuilder.append (File.separator);
		        uriPathBuilder.append (videoName);
		      //  Uri uri = Uri.parse (uriPathBuilder.toString ());
		        Uri uri = Uri.parse("android.resource://"+getPackageName() + "/"+R.raw.introforgame);
		        v.setVideoURI (uri);
		        v.start ();
		  
		    }
		
	    }
