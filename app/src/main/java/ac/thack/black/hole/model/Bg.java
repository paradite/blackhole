package ac.thack.black.hole.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import ac.thack.black.hole.MainGamePanel;



public class Bg {
	private Bitmap bitmap;	// the actual bitmap
	//private int nwidth;			// the X coordinate
	//private int nheight;
	private static final String TAG = MainGamePanel.class.getSimpleName();
	public Bg(Bitmap bitmap, int width, int height) {
		//this.nwidth = width;
		//this.nheight = height;
		//this.bitmap = bitmap;
		this.bitmap = getResizedBitmap(bitmap, width, height, 1);

	}
	
	public void zoombitmap(int width, int height, float zoom){
		Log.d(TAG, "Zoom: "+zoom);
	this.bitmap = getResizedBitmap(bitmap, width, height, zoom);
}

public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight, float zoom) {

int width = bm.getWidth();

int height = bm.getHeight();

float scaleWidth = ( newWidth+(zoom-1)*100) / width;

float scaleHeight = ( newHeight+(zoom-1)*100) / height;

// create a matrix for the manipulation

Matrix matrix = new Matrix();

// resize the bit map

matrix.postScale(scaleWidth, scaleHeight);

// recreate the new Bitmap
//(int)((zoom-1.0)*0.05*width), (int)((zoom-1.0)*0.05*height)
Bitmap resizedBitmap = Bitmap.createBitmap(bm,(int)((zoom-1.0)*0.01*width), (int)((zoom-1.0)*0.01*height), width-2*(int)((zoom-1.0)*0.01*width), height-2*(int)((zoom-1.0)*0.01*height), matrix, false);
//
return resizedBitmap;

}


	//Display BG
	public void draw(Canvas canvas) {
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(20); 
		//String aString = Integer.toString(nwidth) + Integer.toString(nheight) ;
		
		canvas.drawBitmap(bitmap, 0, 0, null);
		//canvas.drawText(aString, 10, 25, paint); 
	}

}