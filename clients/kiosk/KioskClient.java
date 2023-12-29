package clients.kiosk;

import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

/**
 * The standalone Kiosk Client
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
public class KioskClient {
	public static void main (String args[])
	  {
	    String stockURL = args.length < 1         // URL of stock R
	                    ? Names.STOCK_R           //  default  location
	                    : args[0];                //  supplied location
	    
	    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
	    mrf.setStockRInfo( stockURL );
	    displayGUI(mrf);                          // Create GUI
	  }
	   
	  private static void displayGUI(MiddleFactory mf)
	  {
	    JFrame  window = new JFrame();     
	    window.setTitle( "Kiosk Client" );
	    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    
	    KioskModel model = new KioskModel(mf);
	    KioskView  view  = new KioskView( window, mf, 0, 0 );
	    KioskController cont  = new KioskController( model, view );
	    view.setController( cont );

	    model.addObserver( view );       // Add observer to the model
	    window.setVisible(true);         // Display Scree
	  }
}
