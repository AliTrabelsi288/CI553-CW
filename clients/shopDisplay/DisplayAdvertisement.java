package clients.shopDisplay;

import javax.swing.*;

import clients.Picture;

import java.awt.*;

import java.util.Timer;
import java.util.TimerTask;

import java.util.Random;

/**
 * The visual advertisement display seen by customers in the store
 * @author  Ali Trabelsi
 * @version 1.0
 */
public class DisplayAdvertisement extends Canvas
{
  private static final long serialVersionUID = 1L;
  private final int H = 300;         // Height of window 
  private final int W = 300;         // Width  of window 
  private int currentCounter;			 	 // counter for array iteration
  private int lastCounter;			 // last number of counter
  
  private Picture thePicture = new Picture(300,300);	//creates instance of Picture class, sets width/height
  
  
  public DisplayAdvertisement(){
	  
  }
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */
  public DisplayAdvertisement(  RootPaneContainer rpc, int x, int y )
  {
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout( new BorderLayout() );             // Border N E S W CENTER 
    rootWindow.setSize( W, H );                     // Size of Window  
    rootWindow.setLocation( x, y );                 // Position on screen
    rootWindow.add( this, BorderLayout.CENTER );    //  Add to rootwindow

    thePicture.setBounds(0,0, 400, 300);   // Picture area
    cp.add( thePicture );                  //  Add to canvas
    thePicture.clear();
    
    rootWindow.setVisible( true );         // Make visible
    
    timer();	//calls timer method
     
  }
  
  /**
   * Creates object of Timer class called timer
   * Created object of Update class (extends TimerTask class)
   * Sets up timer to call a task every 15 seconds
   */
  public void timer(){
	  Timer timer = new Timer();		//timer object
	  TimerTask task = new Update();	//timertask object
	  
	  timer.scheduleAtFixedRate(task, 0, 15000);	//calls task every 15 seconds
  }
  
  /**
   * Extends TimerTask class
   * Called by the timer 
   * Creates ImageIcons of adverts and cycles through them
   */
  private class Update extends TimerTask{
	  ImageIcon ad1 = new ImageIcon("images/ad1.png");
	  ImageIcon ad2 = new ImageIcon("images/ad2.png");
	  ImageIcon ad3 = new ImageIcon("images/ad3.png");
	  ImageIcon ad4 = new ImageIcon("images/ad4.png");
	  ImageIcon ad5 = new ImageIcon("images/ad5.png");	//ImageIcons of advert images
	    
	  ImageIcon images[] = {ad1, ad2, ad3, ad4, ad5};	//ImageIcon type array to store all images to be indexed
	  
      public void run(){
    	  randomGenerator();							//calls randomGenerator method
    	  
    	  thePicture.set(images[currentCounter]);		//calls Picture classes method set and passes ImageIcon of current advert
      }
  }
  
  /**
   * Randomly picks number between 0-4
   * Used as pointer for advert array
   */
  public void randomGenerator(){
	  Random random = new Random();			//creates random instance
	  currentCounter = random.nextInt(4);	//sets counter to random number between 0-4
	  
	  if(lastCounter == currentCounter){	//if previous counter value is equal to new counter value
		  randomGenerator();				//calls method again - recursion
	  }
	  
	  else{									//lastCounter and currentCounter are different
		  lastCounter = currentCounter;		//sets lastCounter to currentCounter
	  }   
  }
  
  /**
   * Getter method for currentCounter
   * JUnit
   * @return currentCounter
   */
  public int getCurrentCounter(){
	  return currentCounter;	//returns currentCounter
  }
  
  /**
   * Getter method for lastCounter
   * JUnit
   * @return currentCounter
   */
  public int getLastCounter(){
	  return lastCounter;		//returns lastCounter
  }
}
