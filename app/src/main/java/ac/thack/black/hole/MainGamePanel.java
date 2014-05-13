package ac.thack.black.hole;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ViewSwitcher;

import com.google.android.gms.games.Games;

import java.io.IOException;
import java.util.Random;

import ac.thack.black.hole.model.Bg;
import ac.thack.black.hole.model.Model;
import ac.thack.black.hole.model.Planets;
import ac.thack.black.hole.model.Stats;
import ac.thack.black.hole.R;

public class MainGamePanel extends SurfaceView implements
SurfaceHolder.Callback {
private static final String TAG = MainGamePanel.class.getSimpleName();
private ViewSwitcher switcher;
MainThread thread;
private Model blackh;

public Stats stats;

private Bg bg5;
public float zoom=(float) 1.0;
public float currentzoom=(float) 1.0;
public NameActivity ma;
public int sizeimg=100; //size of black hole
public int width;
public int height;
public String name;
public int NoOfPlanet=7; //Actual planet is 3,4,5,6,7. Anti-matter is 0,1,2
public int NoOfAnti=0;

//Special events variables
public int NoOfAntisaved=0;
public boolean special=false; // Special event after sucking special planet
public boolean speciali=false; // Special planet generation boolean
//public int specialt=0;        // Special event time in frames
public double multisave=0;       //Saved current multiplier

//Sound related
public int soundcount=0;

private Planets[] planet= new Planets[9];
private int newx;
private int newy;
private static int level1=500; //500
private static int level2=1000;//500
private static int level3=1500;//500
private static int level4=2000;//500
private static int level5=2400;//400
private static int level6=2800;//400
private static int level7=3100;//300
private static int level8=3400;//300
private static int level9=3600;//200

MediaPlayer AntiPlayer = MediaPlayer.create((NameActivity)getContext(), R.raw.absorbanti);
MediaPlayer SuckPlanetPlayer = MediaPlayer.create((NameActivity)getContext(), R.raw.absorb);
MediaPlayer LevelPlayer = MediaPlayer.create((NameActivity)getContext(), R.raw.lvlup);
MediaPlayer LosePlayer = MediaPlayer.create((NameActivity)getContext(), R.raw.bhlose);
public MainGamePanel(Context context, int width, int height, String name) {
    super(context);
// adding the callback (this) to the surface holder to intercept events
    getHolder().addCallback(this);
    this.width=height;
    this.height=width;
    this.name=name;
    try {
        LosePlayer.prepare();
    } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    try {
        LevelPlayer.prepare();
    } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    try {
        SuckPlanetPlayer.prepare();
    } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    try {
        AntiPlayer.prepare();
    } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


// Create blackhole and background

    bg5 = new Bg(BitmapFactory.decodeResource(getResources(), R.drawable.background), this.width, this.height);

    blackh = new Model(BitmapFactory.decodeResource(getResources(), R.drawable.blackholen), this.width/2, this.height/2);
    stats = new Stats(0,1,this.name, this.width, this.height);

//Initiate ac.thack.blackhole2.app.model.Planets and antimatter
    for(int i=3-NoOfAnti;i<=NoOfPlanet;i++){
        checkstack(i);
    }

//planet[1] = new ac.thack.blackhole2.app.model.Planets(BitmapFactory.decodeResource(getResources(), R.drawable.planet2), randomGenerator.nextInt(this.width-100)+50, randomGenerator.nextInt(this.height-100)+50, this.width, this.height);
//planet[2] = new ac.thack.blackhole2.app.model.Planets(BitmapFactory.decodeResource(getResources(), R.drawable.planet3), randomGenerator.nextInt(this.width-100)+50, randomGenerator.nextInt(this.height-100)+50, this.width, this.height);
//planet[3] = new ac.thack.blackhole2.app.model.Planets(BitmapFactory.decodeResource(getResources(), R.drawable.planet4), randomGenerator.nextInt(this.width-100)+50, randomGenerator.nextInt(this.height-100)+50, this.width, this.height);
//planet[4] = new ac.thack.blackhole2.app.model.Planets(BitmapFactory.decodeResource(getResources(), R.drawable.planet5), randomGenerator.nextInt(this.width-100)+50, randomGenerator.nextInt(this.height-100)+50, this.width, this.height);

//Log.d(TAG, Integer.toString(randomGenerator.nextInt(100)));
//Create ac.thack.blackhole2.app.model.Stats to record stats


// create the game loop thread
    thread = new MainThread(getHolder(), this);

// make the GamePanel focusable so it can handle events
    setFocusable(true);
}


    //Load planets and anti-matter, anti-matter being first
    final int[] planetarray = {
            R.drawable.antimatter,
            R.drawable.antimatter,
            R.drawable.antimatter,
            R.drawable.planet1e,
            R.drawable.planet2e,
            R.drawable.planet3e,
            R.drawable.planet4e,
            R.drawable.planet5e,
            R.drawable.glowplanet,
            R.drawable.planet7e,


    };

    //Planet Type String Array
    final String[] planetname = {
            "",
            "",
            "",
            "Green",
            "Violet",
            "Blue",
            "Yellow",
            "Orange",
            "Special",
    };

    //Initialisation of planets
//Check position of planets for re-spawn
    public void checkstack(int i){
        if(i==8 && speciali==false){
            //Log.d(TAG,"Reduce No Of ac.thack.blackhole2.app.model.Planets");
            NoOfPlanet=7;
        }
        else{
            Random randomGenerator = new Random();
            boolean positionvalidx = false;
            boolean positionvalidy = false;
            this.newx= 0;
            this.newy= 0;
            int count=0;
            //Log.d(TAG,"Index of planet: "+i+" Count: "+count);
            int newxory=0; //Postion - Left right or top bottom
            int direction=0; //1-Left 2-Right 3-Up 4-Down
            while(Math.abs(newx-blackh.getX())<50 || positionvalidx==false || positionvalidy==false){
                newxory= randomGenerator.nextInt(100);
                newx= randomGenerator.nextInt(100);
                newy= randomGenerator.nextInt(100);
                if(newxory<50){//Left or Right
                    newy= randomGenerator.nextInt(this.height-20)+10;
                    if(newx<50){//Left
                        newx=-100;
                        direction=2;
                    }//Move right
                    else{//Right
                        newx=this.width+100;
                        direction=1;
                    }
                }
                else{//Top or Bottom
                    newx= randomGenerator.nextInt(this.width-20)+10;
                    if(newy<50){
                        newy=-100;//Top
                        direction=4;
                    }
                    else{
                        newy=this.height+100;
                        direction=3;
                    }
                }
                positionvalidx=true;
                positionvalidy=true;


                count++;
                //Log.d(TAG,"Count: "+count);
            }
		/*	for(int j=0;j<=NoOfPlanet;j++){
				//if(j==i) continue;
				//else{
				if(Math.abs(newx-planet[j].getX())>20 & Math.abs(newy-planet[j].getY())>20 & j==NoOfPlanet) {
					Log.d(TAG,"X an Y: "+newx+" "+newy);

					positionvalidx=true;
					positionvalidy=true;
				}
				else {
					if(Math.abs(newx-planet[j].getX())>20 & Math.abs(newy-planet[j].getY())>20) {
						continue;
					}
					else break;
				}
			//}
			}
		}

*/
            planet[i] = new Planets(BitmapFactory.decodeResource(getResources(), planetarray[i]), newx, newy, this.width, this.height);
            if(i==0 || i==1 || i==2){
                if(stats.getscore()<level1)
                    planet[i].setSpeed(0);
                else if(stats.getscore()<level2)
                    planet[i].setSpeed(1);
                else if(stats.getscore()<level3)
                    planet[i].setSpeed(2);
                else if(stats.getscore()<level4)
                    planet[i].setSpeed(3);
                else if(stats.getscore()<level5)
                    planet[i].setSpeed(4);
                else if(stats.getscore()<level6)
                    planet[i].setSpeed(5);
                else if(stats.getscore()<level7)
                    planet[i].setSpeed(6);
                else if(stats.getscore()<level8)
                    planet[i].setSpeed(7);
                else if(stats.getscore()<level9)
                    planet[i].setSpeed(8);
                else{
                    planet[i].setSpeed(9);
                }

            }

            if(direction==1){
                if(newy<this.height/2){
                    planet[i].getStatus().setyDirection(1);
                }
                else{
                    planet[i].getStatus().setyDirection(-1);
                }
                planet[i].getStatus().setxDirection(-1);
            }
            if(direction==2){
                if(newy<this.height/2){
                    planet[i].getStatus().setyDirection(1);
                }
                else{
                    planet[i].getStatus().setyDirection(-1);
                }
                planet[i].getStatus().setxDirection(1);
            }
            if(direction==3){
                if(newx<this.width/2){
                    planet[i].getStatus().setxDirection(1);
                }
                else{
                    planet[i].getStatus().setxDirection(-1);
                }
                planet[i].getStatus().setyDirection(-1);
            }
            if(direction==4){
                if(newx<this.width/2){
                    planet[i].getStatus().setxDirection(1);
                }
                else{
                    planet[i].getStatus().setxDirection(-1);
                }
                planet[i].getStatus().setyDirection(1);
            }
            //1-Left 2-Right 3-Up 4-Down
        }
    }


    public int gwidth() {
        return this.getWidth();
    }

    public int gheight() {
        return this.getHeight();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
// at this point the surface is created and
// we can safely start the game loop

        thread.setRunning(true);
        thread.start();

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
// tell the thread to shut down and wait for it to finish
// this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
    }

    //Control System
    public boolean hold=false;
    MotionEvent SaveEvent;

    public void Holding(){
        if(hold){
            blackh.setX((int)SaveEvent.getX());
            blackh.setY((int)SaveEvent.getY());
        }
    }

    //Collision Detection
    public boolean CollisionDetect(){


        for(int i=3-NoOfAnti; i<=NoOfPlanet; i++){
            planet[i].xdis=planet[i].getX()-blackh.getX();
            planet[i].ydis=planet[i].getY()-blackh.getY();
            planet[i].dis=((planet[i].xdis)*(planet[i].xdis)+(planet[i].ydis)*(planet[i].ydis));
            planet[i].dis=(int) Math.sqrt(planet[i].dis);
            planet[i].dis-=(blackh.getBitmap().getWidth()/2+planet[i].getBitmap().getWidth()/2);

            //Log.d(TAG,i+" x distance: "+planet[i].xdis);

            if(planet[i].dis<=0) Suck(i);
        }

        return false;
    }

    //Initiate Special Event by creating special planet
    public void inispecial(){
        Random randomGenerator = new Random();
        if(randomGenerator.nextInt(1000)<2){
            NoOfPlanet=8;
            speciali=true;
            checkstack(8);
        }
    }
    //Start Special Event
    public void startspecial(){
        speciali=false;
        //NoOfAnti=0;
        stats.specialt=250;
        stats.alertspecial=255;
        special=true;
        //NoOfAntisaved=NoOfAnti;
        multisave=stats.multi;
        stats.multi+=multisave;
        playlvlup();
    }

    public void endspecial(){
        stats.multi-=multisave;
	/*if(stats.getscore()<100){
		NoOfAnti=0;
	}
	if(stats.getscore()<level1){
			NoOfAnti=1;
			checkstack(3-NoOfAnti);

	}
	if(stats.getscore()<level2){
				NoOfAnti=2;
				checkstack(3-NoOfAnti);
		}
	if(stats.getscore()>=level2){
			NoOfAnti=3;
			checkstack(3-NoOfAnti);
			}
			*/
        NoOfPlanet=7;
        special=false;
    }
    //Suck System
    public void Suck(int i){

        stats.lastx=planet[i].getX();
        stats.lasty=planet[i].getY();
        if(i==8) {
            startspecial();
            soundsuck();
        }

        if(i!=0&&i!=1&&i!=2){
            soundsuck();
            if(stats.bonustype!=planetname[i]){
                stats.bonustype=planetname[i];
                stats.bonus=1;

            }

            else{
                soundsuck();
                stats.bonus++;
                if(stats.bonus%3==0 && stats.bonus>=3){
                    //stats.bonustype="None";
                    //stats.bonus=0;
                    stats.multi+=0.5;
                    stats.alertmulti=255;
                }
            }
            stats.lastsize=(int)((planet[i].getBitmap().getHeight()/10)*stats.multi);
            //Log.d(TAG,"original: "+planet[i].getBitmap().getHeight()/10);
            //Log.d(TAG,"float: "+(planet[i].getBitmap().getHeight()/10)*stats.multi);
            //Log.d(TAG,"multied: "+stats.lastsize);

            stats.setsize(stats.getsize()+stats.lastsize);
            stats.setscore(stats.getscore()+stats.lastsize);

            stats.alertsize=255;
        }
        else{
            //Anti-matter logic - reduce size by 10X
            soundanti();
            stats.lastsize=(int)((planet[i].getBitmap().getHeight())+(int)stats.getsize()/10);
            //Special event
            if(special) stats.lastsize=0; //Special event affecting effect of anti-matter

            //stats.multi=1.0;

            stats.setsize(stats.getsize()-stats.lastsize);
            stats.lastsize=-stats.lastsize;
            if(!special) stats.alertanti=255;

            stats.alertsize=255;
            //stats.setscore(stats.getscore()+1);
        }
/*	Random randomGenerator = new Random();
	newx= randomGenerator.nextInt(this.width-100)+50;
	newy= randomGenerator.nextInt(this.height-100)+50;
	while(Math.abs(newx-blackh.getX())<50){
		newx= randomGenerator.nextInt(this.width-100)+50;
	}
	while(Math.abs(newy-blackh.getY())<50){
		newy= randomGenerator.nextInt(this.width-100)+50;
	}
	*/
        checkstack(i);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the blackh
            blackh.handleActionDown((int)event.getX(), (int)event.getY());

            // check if in the lower part of the screen we exit
            //if (event.getY() > getHeight() - sizeimg) {
            //	thread.setRunning(false);
            //	((Activity)getContext()).finish();
            //} else {
            //}
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (blackh.isTouched()) {
                hold =true;
                SaveEvent = event;
                // the blackh was picked up and is being dragged
                //blackh.setX((int)event.getX());
                //blackh.setY((int)event.getY());

            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            hold =false;
            if (blackh.isTouched()) {
                blackh.setTouched(false);
            }
            else{
                blackh.setX((int)event.getX());
                blackh.setY((int)event.getY());
            }
        }
        return true;
    }

    //Draw Background, ac.thack.blackhole2.app.model.Stats and Blackhole
    public void render(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);

        //Change background size for zooming effect

//if(stats.getsize()<=999){
//	//zoom=10;
//	zoom=(float)5.0-stats.getsize()/200;
//	DecimalFormat df = new DecimalFormat("#.0");
//	df.format(zoom);
//	}
//else {
//	zoom=1;
//}
//if(zoom!=currentzoom){
//	currentzoom=zoom;
//	bg5.zoombitmap(this.width, this.height, zoom);
//	bg5.draw(canvas);
//}
//else{
        bg5.draw(canvas);
//}
//canvas.drawColor(Color.BLACK);

        for(int i=NoOfPlanet;i>=3-NoOfAnti;i--){
            planet[i].draw(canvas);
        }

        blackh.direction+=10;
        if(blackh.direction==360) blackh.direction=0;
        blackh.draw(canvas);

        stats.draw(canvas);
    }

    //End Game Logic
    public void Endgame(){
        if(stats.getsize()<=0){
            LosePlayer.seekTo(0);
            LosePlayer.start();

            thread.setRunning(false);
            //((Activity)getContext()).finish();
            ((NameActivity)getContext()).onScoreSubmit(getFinalScore());
            ((NameActivity)getContext()).finish();

	/*		super.Endgame(savedInstanceState);
			ma= new ac.thack.black.hole.MainActivity();
	        setContentView(R.layout.activity_main);*/
            //ac.thack.black.hole.MainActivity.gameEnd();
        }
    }

    //Change Difficulty Level by changing number of anti-matter
    public void ChangeDiff(){
        if(stats.getscore()<10){ //Minimum score for anti-matter to appear

        }
        else if(stats.getscore()<level1){
            if(NoOfAnti==0){
                NoOfAnti=1;
                checkstack(3-NoOfAnti);
            }
        }
        else if(stats.getscore()<level2){
            if(NoOfAnti==1){
                NoOfAnti=2;
                checkstack(3-NoOfAnti);
            }
        }
        else if(NoOfAnti==2){
            NoOfAnti=3;
            checkstack(3-NoOfAnti);
        }

    }
    public void playlvlup(){
        if (LevelPlayer.isPlaying()==false){
            LevelPlayer.seekTo(0);
            LevelPlayer.start();
        }
    }

    public void soundsuck(){

        if (SuckPlanetPlayer.isPlaying()==true){
            //SuckPlanetPlayer.stop();
            SuckPlanetPlayer.seekTo(0);
            SuckPlanetPlayer.start();
        }
        else{
            SuckPlanetPlayer.seekTo(0);
            SuckPlanetPlayer.start();
        }
    }
    public void soundanti(){

        if (AntiPlayer.isPlaying()==true){
            //AntiPlayer.stop();
            AntiPlayer.seekTo(0);
            AntiPlayer.start();
            //log.e
        }
        else{
            AntiPlayer.seekTo(0);
            AntiPlayer.start();
        }
    }

    public int getFinalScore(){
        return stats.getscore();
    }

    public void Gravity(){
        for(int i=NoOfPlanet;i>=3-NoOfAnti;i--){
            float distancefactor =1;
            distancefactor=1-planet[i].dis/(getWidth()/9);
            if(distancefactor<0)
                distancefactor=0;
            planet[i].absxdis=Math.abs(planet[i].xdis)-(blackh.getBitmap().getWidth()/2+planet[i].getBitmap().getWidth()/2);
            planet[i].absydis=Math.abs(planet[i].ydis)-(blackh.getBitmap().getWidth()/2+planet[i].getBitmap().getWidth()/2);
            if (planet[i].absydis<1) planet[i].absydis=1;
            if (planet[i].absxdis<1) planet[i].absxdis=1;
            planet[i].dx=(int)((int)(getWidth()/8/(planet[i].absxdis+10))*(1.0+(stats.getsize()+0.1)/500.0)*distancefactor);
            planet[i].dy=(int)((int)(getWidth()/8/(planet[i].absydis+10))*(1.0+(stats.getsize()+0.1)/500.0)*distancefactor);
            if(planet[i].dx!=0) planet[i].dx+=(planet[i].status.xv);
            if(planet[i].dx>5) planet[i].dx=5;
            if(planet[i].dy!=0) planet[i].dy+=(planet[i].status.xv);
            if(planet[i].dy>5) planet[i].dy=5;

            if(planet[i].dis>424) {
                planet[i].dx=0;
                planet[i].dy=0;
            }
            if(i>=3 && special){
                if(i>=3){ //Special event affecting attraction of normal planets and anti-matter
                    planet[i].dx=planet[i].dx+5;
                    planet[i].dy=planet[i].dy+5;
                }
                else{
                    //planet[i].dx=planet[i].dx-2;
                    //planet[i].dy=planet[i].dy-2;
                }
            }

            //Log.d(TAG,i+" xdis: "+planet[i].xdis+" ydis: "+planet[i].ydis);
            //Log.d(TAG,i+" xv: "+dx+" yv: "+dx);

            if(planet[i].xdis<0){
                planet[i].status.gx=planet[i].dx;
                planet[i].status.gxDirection=1;
            }
            else if(planet[i].xdis>0){
                planet[i].status.gx=planet[i].dx;
                planet[i].status.gxDirection=-1;
            }
            if(planet[i].ydis>0){
                planet[i].status.gy=planet[i].dy;
                planet[i].status.gyDirection=-1;
            }
            else if(planet[i].ydis<0){
                planet[i].status.gy=planet[i].dy;
                planet[i].status.gyDirection=1;
            }

        }
    }

    /**
     * This is the game update method. It iterates through all the objects
     * and calls their update method if they have one or calls specific
     * engine's update method.
     */
    public void update() {
        Endgame();
        Holding(); //Detect whether holding
        CollisionDetect();
        Gravity();

        //if(!special)
        ChangeDiff();  //Change difficulty last to create extra anti-matter

        if(!special && !speciali){
            inispecial();


        }
        if(special){
            if(stats.specialt<1){
                endspecial();
            }
            else
                stats.specialt--;
        }

// Update the  black hole
        blackh.update();
        for(int i=3-NoOfAnti;i<=NoOfPlanet;i++){
            //Log.d(TAG,"Planet "+i+ " X: "+planet[i].getX()+" Y: "+ planet[i].getY());
            if (planet[i].getX()>= getWidth()+150) {
                if(i==8)speciali=false;
                checkstack(i);
            }
// check collision with left wall if heading left
            if (planet[i].getX() <= -150) {
                if(i==8)speciali=false;
                checkstack(i);
            }
// check collision with bottom wall if heading down
            if (planet[i].getY()>= getHeight()+150) {
                if(i==8)speciali=false;
                checkstack(i);
            }
// check collision with top wall if heading up
            if (planet[i].getY() <= -150) {
                if(i==8)speciali=false;
                checkstack(i);
            }


        }
        for(int i=NoOfPlanet;i>=3-NoOfAnti;i--){
            //if(planet[i].absxdis<1 && planet[i].dx>0){

            //}
            //else
            planet[i].updategX();
            //if(planet[i].absydis<1 && planet[i].dx>0){

            //}
            //else
            planet[i].updategY();
        }

    }
}