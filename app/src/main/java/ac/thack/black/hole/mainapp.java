package ac.thack.black.hole;

import android.app.Application;
import android.util.Log;


public class mainapp extends Application {
    private static final String TAG = "mainapp";
    public void onCreate() {
        super.onCreate();
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  //code to keep backlight on
        // SoundSystem.SoundEffects();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
