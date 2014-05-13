package ac.thack.black.hole.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import ac.thack.black.hole.status.Status;


public class Model {
	public float direction;
	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	private boolean touched;	// if droid is touched/picked up
	private Status status;	// the status with its directions
	Matrix matrix = new Matrix();	
	public Model(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.status = new Status();
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
		if(x<this.x-25) this.x-= 20;
		if(x>this.x+25) this.x+= 20;
		//this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		if(y<this.y-25) this.y-= 15;
		if(y>this.y+25) this.y+= 15;
		//this.y = y;
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

	public void draw(Canvas canvas) {
		this.matrix.reset();
		this.matrix.setTranslate(x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2));
		this.matrix.postRotate((float)this.direction, this.x, this.y); 
		canvas.drawBitmap(bitmap, matrix, null);
		//canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
		if (!touched) {



			//x += (status.getXv() * status.getxDirection()); 
			//y += (status.getYv() * status.getyDirection());
		}
	}
	
	
	/**
	 * Handles the {@link MotionEvent.ACTION_DOWN} event. If the event happens on the 
	 * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
	 * @param eventX - the event's X coordinate
	 * @param eventY - the event's Y coordinate
	 */
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				// Char touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}

	}
}