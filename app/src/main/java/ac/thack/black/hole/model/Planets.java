package ac.thack.black.hole.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

import ac.thack.black.hole.status.Status;

public class Planets {
	public int dis;         // the distance to blackhole
	public int xdis;
	public int ydis;
	public int absxdis; // Absolute position relative to blackhole
	public int absydis;
	public int dx;  //Speed due to gravity
	public int dy;
	
	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	private boolean touched;	// if droid is touched/picked up
	public Status status;	// the status with its directions
	Random randomGenerator = new Random();

	
	public Planets(Bitmap bitmap, int x, int y, int width, int height) {
		
		//this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.status = new Status();
		
		int ranget = randomGenerator.nextInt(50); //Random Radius
		this.bitmap = getResizedBitmap(bitmap, ranget + 70, ranget + 70);
	}


	public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

		int width = bm.getWidth();

		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;

		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation

		Matrix matrix = new Matrix();

		// resize the bit map

		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap

		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

		return resizedBitmap;

		}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setSpeed(int n) {
		int newspeedx= randomGenerator.nextInt(3)+1+n;
		int newspeedy= randomGenerator.nextInt(3)+1+n;
		this.status.setXv(newspeedx);
		this.status.setYv(newspeedy);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void updateX() {
		//if (!touched) {
			x += (status.getXv() * status.getxDirection());
		//}
	}
	public void updategX() {
		//if (!touched) {
			x += (status.getXv() * status.getxDirection()+status.gxDirection*status.gx);
		//}
	}
	public void updateY() {
		//if (!touched) {
			y += (status.getYv() * status.getyDirection());
		//}
	}
	public void updategY() {
		//if (!touched) {
			y += (status.getYv() * status.getyDirection()+status.gyDirection*status.gy);
		//}
	}
}