package ac.thack.black.hole.status;

import java.util.Random;

public class Status {

	public static final int DIRECTION_RIGHT	= 1;
	public static final int DIRECTION_LEFT	= -1;
	public static final int DIRECTION_UP	= -1;
	public static final int DIRECTION_DOWN	= 1;
	
	public float xv = 1;	// velocity value on the X axis
	public float yv = 1;	// velocity value on the Y axis
	public float gx = 0;	// gravity velocity on the X axis
	public float gy = 0;	// gravity velocity on the Y axis

	private int xDirection = DIRECTION_RIGHT;	
	private int yDirection = DIRECTION_DOWN;
	public int gxDirection = DIRECTION_RIGHT;	
	public int gyDirection = DIRECTION_DOWN;
	Random randomGenerator = new Random();
	
	int newx= randomGenerator.nextInt(100);
	int newy= randomGenerator.nextInt(100);
	int newspeedx= randomGenerator.nextInt(2);
	int newspeedy= randomGenerator.nextInt(2);

	
	public Status() {
		if (newx<=49){
			xDirection = DIRECTION_RIGHT;
		}
		else{
			xDirection = DIRECTION_LEFT;	
		}
		if (newy<=49){
			yDirection = DIRECTION_DOWN;
		}
		else{
			yDirection = DIRECTION_UP;	
		}
		this.xv = newspeedx+1;
		this.yv = newspeedy+1;
	}

	public Status(float xv, float yv) {
		this.xv = xv;
		this.yv = yv;
	}

	public float getXv() {
		return xv;
	}
	public void setXv(float xv) {
		this.xv = xv;
	}
	public float getYv() {
		return yv;
	}
	public void setYv(float yv) {
		this.yv = yv;
	}

	public int getxDirection() {
		return xDirection;
	}
	public void setxDirection(int xDirection) {
		this.xDirection = xDirection;
	}
	public int getyDirection() {
		return yDirection;
	}
	public void setyDirection(int yDirection) {
		this.yDirection = yDirection;
	}

	// changes the direction on the X axis
	public void toggleXDirection() {
		xDirection = xDirection * -1;
	}

	// changes the direction on the Y axis
	public void toggleYDirection() {
		yDirection = yDirection * -1;
	}

}
