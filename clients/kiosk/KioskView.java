package clients.kiosk;


import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Kiosk view.
 * @author  Ali Trabelsi
 * @version 1.0
 */

public class KioskView implements Observer
{
  class Name                              									// Names of buttons
  {
    public static final String CHECK  = "Check";
    public static final String CLEAR  = "Clear";
    public static final String CATALOGUE  = "Catalogue";
    public static final String BUY = "Buy";
    public static final String PAY = "Pay";
  }

  private static final int H = 550;       									// Height of window pixels
  private static final int W = 800;       									// Width  of window pixels

  private final JLabel      theCheckAction  = new JLabel();
  private final JTextField  theCheckInput   = new JTextField();
  private final JTextArea   theCheckOutput  = new JTextArea();
  private final JScrollPane theCheckSP      = new JScrollPane();			//Catalogue 
  
  private final JLabel      theCatalogueAction  = new JLabel();
  private final JTextField  theCatalogueInput   = new JTextField();
  private final JTextArea   theCatalogueOutput  = new JTextArea();
  private final JScrollPane theCatalogueSP      = new JScrollPane();		//Check
  
  private final JLabel      theBuyAction  = new JLabel();
  private final JTextField  theBuyPnInput   = new JTextField();
  private final JTextField  theBuyCardInput   = new JTextField();
  private final JTextField  theBuyMMInput   = new JTextField();
  private final JTextField  theBuyYYInput   = new JTextField();
  private final JTextField  theBuyCVCInput   = new JTextField();
  private final JTextArea   theBuyOutput  = new JTextArea();
  private final JScrollPane theBuySP      = new JScrollPane();				//Buy
  
  
  
  private final JButton     theBtCatalogue = new JButton( Name.CATALOGUE );
  private final JButton     theBtCheck = new JButton( Name.CHECK );
  private final JButton     theBtClear = new JButton( Name.CLEAR );			
  private final JButton     theBtBuy = new JButton( Name.BUY );
  private final JButton     theBtPay = new JButton( Name.PAY );				//Buttons

  private Picture thePicture = new Picture(80,80);
  private StockReader theStock   = null;
  private KioskController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  
  @SuppressWarnings("null")
public KioskView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             
    {      
      theStock  = mf.makeStockReader();             		// Database Access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    		// Content Pane
    Container rootWindow = (Container) rpc;         		// Root Window
    cp.setLayout(null);                             		// No layout manager
    rootWindow.setSize( W, H );                     		// Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  		// Font f is
    
    
    theCatalogueAction.setBounds( 110, 5 , 270, 20 );       
    theCatalogueAction.setText( "Search by Product Type" );  
    cp.add( theCatalogueAction );                            
    
    theCatalogueInput.setBounds( 110, 25+60*0, 270, 40 );         
    theCatalogueInput.setText("");                           
    cp.add( theCatalogueInput );                             
    
    theCatalogueSP.setBounds( 110, 25+60*1, 270, 140 );         
    theCatalogueOutput.setText( "" );                        
    theCatalogueOutput.setFont( f );                         
    cp.add( theCatalogueSP );                                
    theCatalogueSP.getViewport().add( theCatalogueOutput );           
    
    theBtCatalogue.setBounds( 16, 25+60*0, 80, 40 );    
    theBtCatalogue.addActionListener(                   
      e -> cont.doCatalogue( theCatalogueInput.getText() ) );
    cp.add( theBtCatalogue);                           			//Catalogue 
    
   
    theCheckAction.setBounds( 110, 240 , 270, 20 );       
    theCheckAction.setText( "Search by Product Number" );  
    cp.add( theCheckAction );                           

    theCheckInput.setBounds( 110, 25+60*4, 270, 40 );         
    theCheckInput.setText("");                           
    cp.add( theCheckInput );                             
    
    theCheckSP.setBounds( 110, 25+60*5, 270, 160 );          
    theCheckOutput.setText( "" );                       
    theCheckOutput.setFont( f );                          
    cp.add( theCheckSP );                                
    theCheckSP.getViewport().add( theCheckOutput );           

    thePicture.setBounds( 16, 25+60*5, 80, 80 );   
    cp.add( thePicture );                           
    thePicture.clear();
    
    theBtCheck.setBounds( 16, 25+60*4, 80, 40 );    
    theBtCheck.addActionListener(                   
      e -> cont.doCheck( theCheckInput.getText() ) );
    cp.add( theBtCheck );                  						//Check         
    
    
    theBuyAction.setBounds( 500, 5 , 270, 20 );       
    theBuyAction.setText( "Buy Items" );  
    cp.add( theBuyAction );                            
    
    theBuyPnInput.setBounds( 500, 25+60*0, 270, 40 );         
    theBuyPnInput.setText("Product Number");                           
    cp.add( theBuyPnInput );  
    
    theBtBuy.setBounds( 400, 25+60*0, 80, 40 );    		
    theBtBuy.addActionListener(                   		
      e -> cont.doBuy( theBuyPnInput.getText()) );
    cp.add( theBtBuy );                           				
    
    theBuyCardInput.setBounds( 500, 25+60*3, 270, 40 );         
    theBuyCardInput.setText("Card Number");                           
    cp.add( theBuyCardInput );           						                	
    
    theBuyMMInput.setBounds( 500, 25+60*4, 50, 40 );         
    theBuyMMInput.setText("MM");                           
    cp.add( theBuyMMInput ); 							
    
    theBuyYYInput.setBounds( 575, 25+60*4, 50, 40 );         
    theBuyYYInput.setText("YY");                           
    cp.add( theBuyYYInput );
    
    theBuyCVCInput.setBounds( 645, 25+60*4, 125, 40 );         
    theBuyCVCInput.setText("Card CVC");                           
    cp.add( theBuyCVCInput ); 							
    
    theBuySP.setBounds( 500, 25+60*5, 270, 160 );       
    theBuyOutput.setText( "" );                        	
    theBuyOutput.setFont( f );                         	  
    cp.add( theBuySP );                                	
    theBuySP.getViewport().add( theBuyOutput );        	
 
    theBtPay.setBounds( 400, 25+60*3, 80, 40 );    		
    theBtPay.addActionListener(                   		
      e -> cont.doPay( theBuyCardInput.getText() ,theBuyMMInput.getText(), theBuyYYInput.getText(), theBuyCVCInput.getText()));
    cp.add( theBtPay );                           				//Buy and Pay
    
    
    theBtClear.setBounds( 16, 25+60*7, 80, 40 );    
    theBtClear.addActionListener(                   
      e -> cont.doClear() );
    cp.add( theBtClear );                           			//Clear
    
    
    rootWindow.setVisible( true );                  			//Make visible
    theCatalogueInput.requestFocus();               			//Sets focus to catalogue input
    
    cp.setBackground(Color.GRAY);								//Sets background colour
    
  }

   /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */
  public void setController( KioskController c )
  {
    cont = c;
  }

  
  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  public void update( Observable modelC, Object arg )
  {
    KioskModel model  = (KioskModel) modelC;				//Kiosk model
    String     returnMessage = (String) arg;				//The return message from model
    String 	   returnType = returnMessage.substring(0, 1);	//Sub-strings the first character of the return value - used in switch case
    String 	   returnValue = returnMessage.substring(1);	//Sub-strings the return value from after the first character - used to store actual message
    
    
    switch (returnType) {									//Switch case to determine which method return originated for - will then update corr
    	case "1":											//Catalogue
    		theCatalogueOutput.setText(returnValue);		//Sets output to the returned value - adds to scroll pane
    	    theCheckInput.requestFocus();      				//Focus moves to check input
    		break;
    		
        case "2":											//Check
        	ImageIcon image = model.getPicture();  			//Image of product
            if ( image == null ){							//If no image set in model
              thePicture.clear();                  			//Clear picture
            } 
            else{											//Image set in model
              thePicture.set(image);             			//Sets picture
            }
            
            theCheckOutput.setText(model.getBasket().getDetails());	//Sets output to the details of the basket - the product searched
            theCheckInput.requestFocus();              		 // Focus is here
        	break;
        	
        case "3":											//Buy
        	theBuyOutput.setText(returnValue);				//Sets buy output to the returned message from doBuy
        	theBuyCardInput.requestFocus();					//Sets focus to the card number input
        	break;
        	
        case "4":											//Pay
        	theBuyOutput.setText(returnValue);				//Sets output to returned value
        	
        	theBuyPnInput.setText("Product Number");		//Sets buy pn input to original value
        	theBuyCardInput.setText("Card Number");			//Sets buy card input to original value
        	theBuyMMInput.setText("MM"); 					//Sets buy MM input to original value
        	theBuyYYInput.setText("YY");					//Sets buy YY input to original value
        	theBuyCVCInput.setText("Card CVC");				//Sets buy CVC input to original value
        	
        	theBuyPnInput.requestFocus();					//Sets focus to the product number input
        	break;
        	
        default:											//Clear
        	String[] type = returnValue.split(",");			//Splits returns value into 2 varibales stored in an array
        	theCatalogueAction.setText(type[0]);			//Sets action to first variable
        	theCheckAction.setText(type[1]);				//Sets action to second variable
        	theCatalogueInput.setText("");					//Clears input
        	theCatalogueOutput.setText("");					//Clears output
        	theCheckInput.setText("");						//Clears input
            theCheckOutput.setText("");						//Clears output
            thePicture.clear();								//Clears picture
            theBuyPnInput.setText("");						//Clears input
            theBuyCardInput.setText("");					//Clears input
        	theBuyMMInput.setText(""); 						//Clears input
        	theBuyYYInput.setText("");						//Clears input
        	theBuyCVCInput.setText("");						//Clears input
            
            theCatalogueInput.requestFocus();				//Focus moves to catalogue
    }	
  }

}
