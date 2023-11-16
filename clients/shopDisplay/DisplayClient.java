package clients.shopDisplay;

import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

/**
 * The standalone shop Display Client.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
public class DisplayClient
{
   public static void main (String args[])
   {
     String stockURL = args.length < 1     // URL of stock RW
                     ? Names.STOCK_RW      //  default  location
                     : args[0];            //  supplied location
     String orderURL = args.length < 2     // URL of order
                     ? Names.ORDER         //  default  location
                     : args[1];            //  supplied location
     
    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
    mrf.setStockRWInfo( stockURL );
    mrf.setOrderInfo  ( orderURL );        //
    displayGUI(mrf);                       // Create GUI
    displayAds();						   // Create GUI for Advertisement screen
    
  }
  
  private static void displayGUI(MiddleFactory mf)
  {     
    JFrame  window = new JFrame();

    window.setTitle( "Pick Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    
    DisplayModel    model = new  DisplayModel(mf);
    DisplayView     view  = new  DisplayView( window, mf, 0, 0 );
    DisplayController cont  = new DisplayController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Display Screen 
    
  }
  
  /**
   * Creates new instance of DisplayAvertisements
   * Can be run isolated from rest of CatShop if called from DisplayClient
   */
  private static void displayAds() {
	  JFrame  window = new JFrame();	//creates new window for advertisements
	  
	  window.setTitle( "Advertisements Client");	//sets title of window to Advertisements Client
	  window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	//closes window when exited
	  
	  DisplayAdvertisement ads = new DisplayAdvertisement(window, 0, 0);	//instance of DisplayAdvertisements - passes window object and coordinates
	  window.setVisible(true);         // Display Screen 
  }
}
