package ac.thack.black.hole.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.text.DecimalFormat;

import ac.thack.black.hole.MainActivity;

public class Stats{
	private static final String TAG = MainActivity.class.getSimpleName();

	private int score=0;	//Game Score	
	private int size;	
	private int width;	
	private int height;	
	public double multi;
	private String name;
	
	//Global Gravity
	public static final int DIRECTION_RIGHT	= 1;
	public static final int DIRECTION_LEFT	= -1;
	public static final int DIRECTION_UP	= -1;
	public static final int DIRECTION_DOWN	= 1;
	private int gx;
	private int gy;
	public int bonus;
	public String bonustype;
	public int alertanti=0;
	public int alertmulti=0;
	public int alertsize=0;
	public int alertspecial=0;
	public int specialt=0;
	public int lastsize=0;
	public int lastx=0;
	public int lasty=0;
	DecimalFormat df2 = new DecimalFormat("0.00");
	public Stats(int score, int size, String name, int width, int height) {
		this.name = name;
		this.size = size;
		this.score = score;
		this.width = width;
		this.height = height;
		this.bonus = 0;
		this.bonustype="None";
		this.multi=1.0;
	}
	
	public int getgx() {
		return gx;
	}
	
	public void setgxleft() {
		this.gx = DIRECTION_LEFT;
	}
	public void setgxright() {
		this.gx = DIRECTION_RIGHT;
	}
	public int getgy() {
		return gy;
	}
	
	public void setgydown() {
		this.gy = DIRECTION_DOWN;
	}
	
	public void setgyup() {
		this.gy = DIRECTION_UP;
	}
	
	
	public void setname(String name){
		this.name= name;
	}
	
	public String getname(){
		return this.name;
	}
	
	public int getsize() {
		return size;
	}
	public void setsize(int size) {
		this.size = size;
	}

	public int getscore() {
		return score;
	}
	public void setscore(int score) {
		this.score = score;
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(25); 
		//Log.d(TAG, Integer.toString(mg.gheight()));
		//Log.d(TAG, Integer.toString(this.height));
		DecimalFormat df = new DecimalFormat("#.0");
		String nameString = "Black Hole "+name;
		String sizeString = "Gravity: " + size;
		String multiString = "Multiplier: "+df.format(multi);
		String scoreString ="Score: " + score;
		String multiup = "Multiplier +0.5";
		String special1 ="Special planet abosorb!";
		//String special2 ="Special mode will be activated for 5 seconds!";
		String antiup = "Hit by anti-matter! Strength reduced!"; 
		String Bonus ="Planet "+bonustype+" consecutive count: " + bonus+" of "+3*(1+bonus/3);
		String sizeup ="+"+lastsize;
		if(lastsize<0) sizeup =""+lastsize;
		if(lastsize==0) sizeup ="-"+lastsize;
//Uncoloured texts
		canvas.drawText(nameString, 10, 25, paint); 
		canvas.drawText(sizeString, 10, 55, paint);
		canvas.drawText(multiString, this.width/2-60, 25, paint); 
		canvas.drawText(scoreString, this.width-130, 25, paint); 

//Coloured texts
		if(bonustype=="Green")  paint.setColor(Color.GREEN);
		if(bonustype=="Violet")  paint.setColor(Color.argb(255, 208,32,144));
		if(bonustype=="Blue")  paint.setColor(Color.argb(255, 0,191,255));
		if(bonustype=="Yellow")   paint.setColor(Color.YELLOW);
		if(bonustype=="Orange")   paint.setColor(Color.argb(255, 255,165,0));
		if(bonustype=="Special")   paint.setColor(Color.WHITE); 
		canvas.drawText(Bonus, this.width/2-60, 55, paint); 
		if(alertanti>25){
			paint.setColor(Color.argb(alertanti, 255,0,0));
			canvas.drawText(antiup, this.width/2-60, 85, paint);
			alertanti-=2;
		}
		if(specialt>0){
			paint.setTextSize(25);
			paint.setColor(Color.argb(255, 255,255,255));
			double timeleft=(specialt+0.01)*0.02;
			
			canvas.drawText("[INVINCIBILITY] [DOUBLE MULTIPLIER]", this.width/2-210, this.height-60, paint);
			canvas.drawText("Ending in "+df2.format(timeleft)+"s.", this.width/2-90, this.height-30, paint);
		}
		/*
		if(alertspecial>25){
			paint.setTextSize(25); 
			paint.setColor(Color.argb(alertspecial-25, 255,255,255));
			canvas.drawText(special1, this.width/2-150, this.height-70, paint);
			//canvas.drawText(special2, this.width/2-250, this.height-100, paint);
			
			alertspecial-=2;
		}
		*/
		if(alertmulti>25){
			paint.setTextSize(35); 
			paint.setColor(Color.argb(alertmulti-25, 255,215,0));
			canvas.drawText(multiup, this.width/2-130, 135, paint);
			alertmulti-=3;
			//Log.d(TAG,"Multi:  "+alertmulti);
			paint.setTextSize(25); 
		}
		if(alertsize>25){
			paint.setTextSize(35); 
			if(lastsize>0)
			paint.setColor(Color.argb(alertsize, 255,215,0));
			if(lastsize<0)
			paint.setColor(Color.argb(alertsize, 255,0,0));
			if(lastsize==0)
			paint.setColor(Color.argb(alertsize, 255,255,255));
			if(lastx<0) lastx=100;
			else if(lastx<50) lastx=lastx+100;
			canvas.drawText(sizeup, lastx-50, lasty, paint);
			alertsize-=5;
			paint.setTextSize(25); 
		}
		paint.setColor(Color.WHITE);
		//canvas.drawText(pString, 400, 25, paint); 
	}
}
