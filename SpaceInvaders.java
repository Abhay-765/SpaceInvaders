SI
/////
import java.util.ArrayList; 
import ddf.minim.*;
//class variables
Minim minim;
AudioSample player;

PFont f;
ArrayList<Alien> aliens = new ArrayList<Alien>();
ArrayList<Shot> shot = new ArrayList<Shot>();
Alien aObj;
PImage al; 
PImage shooter;
int x=10;
int y=10;
int shooterX;
int shooterY=350;
boolean moveRight;
boolean moveLeft;

boolean shoot;
int moveX;
boolean moveAliensRight=true;
boolean moveAliensLeft=false;
boolean start =false;
int moveAlien=10;
boolean startOfGame=true;
int points=0;
boolean restart=true;
int anum;
int rowOfAlien=0;


void setup()//initialize variables
{
  size(600,400);
  background(250,0,0);
  moveX=20;
  shoot=false;
  moveRight=false;
  moveLeft=false;
  shooterX=300;
 
  shooter=loadImage("shooter.png");
    f = createFont("Arial",16,true); // STEP 3 Create Font
  
  for(int i=0; i<6; i++){
    if(i==6){
      y+=35;
      x=10;
   rowOfAlien();
    }
  }
    
    aObj=new Alien(x,y);
    aliens.add(aObj);
    x=x+30;
    
  rowOfAlien=0;
  x=10;
  
  
   // Audiotoolkit 
  minim = new Minim (this);
  player = minim.loadSample ("shoot.wav");
}

void draw()
{
  
  if(start){
  background(0 ,0,0);
  for(int i=0; i<shot.size(); i++){
      Shot myShot=shot.get(i);
  if(myShot.shoot==true){
  image(myShot.s,myShot.x,myShot.y);
  myShot.y=myShot.y-5;
  if(myShot.y<-20)
    shoot=false;
  }
  
  }//end of for loop
  
  if(moveRight)
     shooterX+=5;
     
     if(moveLeft)
       shooterX-=5;
  image(shooter,shooterX, shooterY);
  
  for(int i=0; i<aliens.size(); i++)
  {
    Alien A=aliens.get(i);
   moveAlien(A.x,A.y);
   A.x=moveAlien;
    image(A.a,x,A.y);
   
    // println("aliens x " + A.a.height );
  }
  text("Your score is: " + points,10,350);
  
  
  dropDown();
   for(int i=0; i<aliens.size(); i++)
  {
    Alien A=aliens.get(i);
    checkForHit(A.x,A.y,i);
    if(A.y>=400){
  if(aliens.size()<=12)
      start=false;
       aliens.remove(anum);
    shot.remove(i);
  }
  }  
  
  }//end of start equals true
  else{
    if(startOfGame)
       text("ARE YOU READY!! Click Mouse to begin space invasion!",10,100); 
       else
         text("Game Over!    Click Mouse to start over!",10,100);
         
         start=false;
  }//end of start equals false
}//end of draw method

void mousePressed()
{
  
  startOfGame=false;
  start=true;
  
  x=10;
  y=10;
  for(int i=0; i<12; i++){
    if(i==6){
      y+=35;
      x=10;
    }
    aObj=new Alien(x,y);
    aliens.add(aObj);
    x=x+30;
    
  }
  x=10;
  points=0;
  
  
   // Audiotoolkit 
  minim = new Minim (this);
  player = minim.loadSample ("shoot.wav");
  /*
  if(mouseX<300)
    aliens.remove(0);
  else
    aliens.add(al);

*/

}

void keyPressed(){
     if (key == CODED) {
            if (keyCode == RIGHT) {
               //moveRight
                moveRight=true;
            }
        if (keyCode == LEFT) {
              //moveLeft
                moveLeft=true;
             }
             
             
  
   }//end of key if statement
      if(shoot==false){
       
           if(keyCode==32){
               
               player.trigger ();
               int  shotX=(shooterX+shooter.width/2)-(10/2);
                Shot sObj=new Shot(shotX,350);
                shot.add(sObj);
                sObj.shoot=true;
             }
      }
  
}//end of keypressed


void keyReleased(){
    moveRight=false;
    moveLeft=false;
  
  //meeee
}

void checkForHit(int ax,int ay, int anum){
  //
  if(shot.size()>0){
    for(int i=0; i<shot.size(); i++){
      Shot myShot=shot.get(i);
      if(anum<aliens.size()){
  if(myShot.y>ay && myShot.y<ay+aliens.get(anum).a.height  && myShot.x>ax && myShot.x<ax+aliens.get(anum).a.width){
    myShot.shoot=false;
    if(aliens.size()<=1)
      start=false;
    aliens.remove(anum);
    shot.remove(i);
    points=points+10;
  }//end of check for hit if statement
    }//end of for loop
  }//shot size is greater then zero
  
  /**/
}//shot size is greater than zero
}//end of the method

void moveAlien(int xx,int yy){
  moveAlien=xx;
  
   if(moveAliensRight==true){
         
          moveAlien+=3;
         x=moveAlien;    
     } else{
        moveAlien-=3;
        x=moveAlien;
      
    }
  
  
}


void dropDown(){
   if(moveAliensRight==true){
      if(aliens.get(aliens.size()-1).x>575){
         for(int i=0; i<aliens.size(); i++)
          {
            Alien A=aliens.get(i);
            A.y+=10;
          }
            moveAliensRight=false;
            moveAliensLeft=true;
       }
       
       
   }else{
       if(aliens.get(0).x<0){
         for(int i=0; i<aliens.size(); i++)
          {
            Alien A=aliens.get(i);
            A.y+=10;
          }
         
          moveAliensLeft=false;
          moveAliensRight=true;
      }
     
     
   }
  
}


void stop()
{
  // always close Minim audio classes when you are done with them
  player.close();
  minim.stop();
 
  super.stop();
}
 
 void rowOfAlien(){
  x=10;
  y=10;
  
  
   for(int i=0; i<6; i++){
     i=i+6;
    if(i==6){
      y+=35;
      x=10;
    
      
}
 }
 }
//
Alien sub class
//

public class Alien{
  PImage a;
  int x;
  int y;
  
  public Alien(int x, int y){
    this.x=x;
    this.y=y;
    a=loadImage("alien1.png");


  }
}
//
Shot sub class
public class Shot{
  PImage s;
  int x;
  int y;
  boolean shoot;
  
  public Shot(int x, int y){
    this.x=x;
    this.y=y;
    s=loadImage("shot.png");
  }
}
//

