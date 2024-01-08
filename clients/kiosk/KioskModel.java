package clients.kiosk;
import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReadWriter;

import javax.swing.*;
import java.util.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Calendar;


/**
 * Implements the Model of the kiosk client
 * @author  Ali Trabelsi
 * @version 1.0
 */
public class KioskModel extends Observable {
	
	  private Product     theProduct = null;          // Current product
	  private Basket      theBasket  = null;          // Bought items

	  protected String      pn = "";                    // Product being processed
	  protected String 		theAction="";

	  private StockReadWriter theStock     = null;
	  private OrderProcessing theOrder     = null;
	  private ImageIcon       thePic       = null;

	  private HashMap<String, ArrayList<String>> productAlias = new HashMap<>();	//Product HashMap - String of ArrayLists
	  
	  /**
	   * Populates productAlias with a product type string and an ArrayList of it's aliases
	   */
	  protected void generateHashMap() {
		  productAlias.put("Television", new ArrayList<String>(Arrays.asList("television", "tv", "TV", "telly", "t.v")));
		  productAlias.put("Radio", new ArrayList<String>(Arrays.asList("radio", "receiver", "AM", "FM")));
		  productAlias.put("Toaster", new ArrayList<String>(Arrays.asList("toaster", "appliance", "taoster")));
		  productAlias.put("Watch", new ArrayList<String>(Arrays.asList("watch", "jewelry", "wrist")));
		  productAlias.put("Camera", new ArrayList<String>(Arrays.asList("camera", "picture", "digital")));
		  productAlias.put("Music", new ArrayList<String>(Arrays.asList("music", "player", "MP3", "mp3")));
		  productAlias.put("USB", new ArrayList<String>(Arrays.asList("usb", "storage", "memory", "drive")));				//Populates HashMap 
		  
	  }
	  
	  /*
	   * Construct the model of the Customer
	   * @param mf The factory to create the connection objects
	   */
	  public KioskModel(MiddleFactory mf)
	  {
	    try                                          // 
	    {  
	      theStock = mf.makeStockReadWriter();           // Database access
	      theOrder = mf.makeOrderProcessing();
	    } catch ( Exception e )
	    {
	      DEBUG.error("KioskModel.constructor\n" +
	                  "Database not created?\n%s\n", e.getMessage() );
	    }
	    theBasket = makeBasket();                    // Initial Basket
	    generateHashMap();							//Calls generateHashMap to populate productAlias
	  }
	  
	  /*
	   * Constructor method for JUnit
	   */
	  public KioskModel(){
		  
	  }
	  /**
	   * return the Basket of products
	   * @return the basket of products
	   */
	  public Basket getBasket()
	  {
	    return theBasket;
	  }
	  
	  
	  /**
	   * Checks the product number of a product type
	   * @param productType The product type
	   */
	  public void doCatalogue(String productType) { 
		  pn = productType.trim();												//Sets pn to the variable passed - trimmed
		  String theAction = "";												//Sets action to clear
		  	
		  if(checkAlias()){														//If checkAlias returns true
			  try{																//Try function
				  ArrayList<Product> pr = theStock.getNumber( pn );				//ArrayList of products called pr calls getNumber method
				   
				  int count = 0;												//Sets count to 0 - used to iterate over pr
				  
				  for(int i = 0; i <=pr.size()-1; i++){							//For each value in pr
					  theAction += 												//Adds to the value existing in theAction
							  String.format("%s: %s",
									  pr.get(count).getProductNum(),			//Product num of the current Product object - pointed to by count
									  pr.get(count).getDescription()) + "\n";	//Description of the current Product object - pointed to by count
					  
					  count++;													//Increments count by one
				  }
					  
			  }
			  
			  catch(StockException e){											//Catch issues
				      DEBUG.error("KioskClient.doCatalogue()\n%s",
				    		  e.getMessage()
				      );
		      }
		  }
		  else{											//CheckAlias false
			  theAction = "No matching products exist";		//Sets action to no matching products
		  }
         
		  theAction = "1" + theAction;						//Pre-pends 1 to theAction string
		  setChanged(); notifyObservers(theAction);			//Notifies observers (View) and passes theAction as parameter
	  }
	  
	  
	  /**
	   * Iterates through productAlias to match user entry to a product type
	   * @return True/False Found or not found
	   */
	  public boolean checkAlias(){
		  if(productAlias.containsKey(pn)){									//If and key of HashMap equals pn
			  return true;													//True
		  }
			  
		  else{
			  for (String i : productAlias.keySet()) {						//for each HashMap String
				  ArrayList<String> currentIteration = new ArrayList<>();	//Temporary ArrayList to store this iterations ArrayList values
				  currentIteration.addAll(productAlias.get(i));				//Adds this iterations ArrayList values into temporary ArrayList
				  if(currentIteration.contains(pn)) {						//If temporary ArrayList contains pn
					  pn = i;												//Sets pn to correct product type value - to be queried
					  return true;											//True
				  }
				}
			  }
		  
		  return false;														//False
		  }
		
	  
	/**
	 * Check if the product is in Stock
	 * @param productNum The product number
	 */
	public void doCheck(String productNum )
	  {
	    theBasket.clear();                          // Clear s. list
	    String theAction = "";
	    pn  = productNum.trim();                    // Product no.
	    int    amount  = 1;                         //  & quantity
	    try
	    {
	      if ( theStock.exists( pn ) )              // Stock Exists?
	      {                                         // T
	        Product pr = theStock.getDetails( pn ); //  Product
	        if ( pr.getQuantity() >= amount )       //  In stock?
	        { 
	          theAction =                           //   Display 
	            String.format( "%s : %7.2f (%2d) ", //
	              pr.getDescription(),              //    description
	              pr.getPrice(),                    //    price
	              pr.getQuantity() );               //    quantity
	          pr.setQuantity( amount );             //   Require 1
	          theBasket.add( pr );                  //   Add to basket
	          thePic = theStock.getImage( pn );     //    product
	        } else {                                //  F
	          theAction =                           //   Inform
	            pr.getDescription() +               //    product not
	            " not in stock" ;                   //    in stock
	        }
	      } else {                                  // F
	        theAction =                             //  Inform Unknown
	          "Unknown product number " + pn;       //  product number
	      }
	    } catch( StockException e )
	    {
	    	
	      DEBUG.error("KioskClient.doCheck()\n%s",
	      e.getMessage()
	      );
	    }
	    theAction = "2" + theAction;				//Pre-pends 2 to theAction string
	    setChanged(); notifyObservers(theAction);
	  }

	
	  /**
	   * Checks a product number exists and is in stock before an order is placed
	   * Remembers productNum for next stage - doPay
	   * @param productNum The product number
	   */
      public void doBuy(String productNum){
  	    String theAction = "";												//Sets theAction to clear
  	    pn  = productNum.trim();                    						//Product number
  	    int    amount  = 1;                         						//Quantity of the item - one
  	    
  	    try{																//Try
  	    	if(theStock.exists(pn)) {										//If the entered product exists
  	    		Product pr = theStock.getDetails( pn ); 					//Creates Product object of item - temporary to use getQuantity method
  		        if(pr.getQuantity() >= amount){								//Is the quantity of item greater than one - in stock
  		        	theAction = "Product in Stock, Please Enter Card Details";	//Updates theAction - in stock
  		        	theProduct = pr;                     				    //Remembers product
  		        	theProduct.setQuantity(amount);							//Sets quantity to buy to one
  		        }
  		        else{														//Product out of stock
  		        	theAction = "Sorry, Product out of Stock";				//Updates theAction - out of stock
  		        }
  	    	}
  	    	else{															//Product doesnt exist
  	    		theAction = "No Products Match Entered Product Number";		//Updates theAction - no matching product
  	    	}
  	    }
  	    catch(StockException e){											//Catch any issues - throws stock exception
  	    	DEBUG.error("KioskClient.doBuy()\n%s",
  	    	      e.getMessage()
  	    	);
  	    }
  	    theAction = "3" + theAction;										//Pre-pends 3 to theAction string
	    setChanged(); notifyObservers(theAction);
      }
      
      
      /**
       * Validates the entered card details and places an order
       * @param cardNum	Card Number
       * @param cardExp	Card Expiration
       * @param CVC		Card CVC
       */
      public void doPay(String cardNum, String MM, String YY, String CVC){
    	  theBasket = null;														//Sets theBasket to null
    	  String theAction = "";												//Sets theAction to clear						
    	  int    amount  = 1;													//Amount is one
    	  try {																	//True
    		  if(theProduct != null) {											//If theProduct is not null - product selected at buy button
    			  Calendar calendar = Calendar.getInstance();					//Created instance of calendar
    	    	  int todayMonth = (calendar.get(Calendar.MONTH)+1); 			//int value of todays month
    	    	  int todayYear = (calendar.get(Calendar.YEAR) % 100);			//int value of todays year
    	    	  
    	    	  String cardNumber = cardNum.trim();							//String value of the card number from user
    	    	  String cardCVC = CVC.trim();									//String value of the card CVC from user
    	    	  int mm = Integer.parseInt(MM);								//Casted value of the expiry month
    	    	  int yy = Integer.parseInt(YY);								//Casted value of the expiry year
    	    	  		
    	    	  if(cardNumber.length() == 16 && cardCVC.length() == 3 && (mm > todayMonth || yy > todayYear)){	//Checks length of card values and the cards month and date against the days month and date - validation
    	    		  boolean stockBought = theStock.buyStock(theProduct.getProductNum(),theProduct.getQuantity()); //Uses buyStock() to take stock out of the stock level - true if success/false if not  	      
    	    		        if(stockBought){            						//True                           
    	    		          makeBasketIfReq();                    			//Creates new basket to put product in for order
    	    		          theBasket.add(theProduct);          				//Adds product to new basket
    	    		          
    	    		          theOrder.newOrder(theBasket);       				//Creates an order with theBasket
    	    		          theBasket = null;									//Cleares the basket
    	    		          
    	    		          theAction = "Order Successfully Placed";			//Sets theAction - order placed
    	    		        }
    	    		        else{                              					//False 
    	    		          theAction = "Product no longer in stock";       	//Sets theAction - product out of stock
    	    		        }
    	    	  }
    	    	  else{															//Issue with card details - false
    	    		  theAction = "Invalid Card Details Entered";				//Sets theAction - invalid card
    	    	  }
    		  }
    		  else{																//Product not set
    			  theAction = "No product selected - Please complete the buy section above";	//Sets theAction - no product selected
    		  }
    		  
    	  }catch(StockException e){												//Catches any issue - throws stock exception
    		  DEBUG.error("KioskClient.doPay()\n%s",
      	    	      e.getMessage()
      	    	);
    		  
    	  }catch(OrderException e){												//Catches any issues - throws order exception
    		  DEBUG.error("KioskClient.doPay()\n%s",
      	    	      e.getMessage()
      	       );
		}
    	  theAction = "4" + theAction;											//Pre-pends 4 to theAction string
  	      setChanged(); notifyObservers(theAction);
      }
      
      
	  /**
	   * Clear the products from the basket
	   * Update the GUI 
	   */
	  public void doClear()
	  {
	    String theAction = "";												//Clears theAction
	    
	    if(theBasket != null) {												//If basket is not empty
	    	theBasket.clear();  											//Clear basket
	    }                      							
	    
	    theAction = " Search by Product Type,Search by Product Number";     //Set display text
	    thePic = null;                            							//Removes picture
	    setChanged(); notifyObservers(theAction);
	  }
	  
	  
	  /**
	   * Return a picture of the product
	   * @return An instance of an ImageIcon
	   */ 
	  public ImageIcon getPicture()
	  {
	    return thePic;
	  }
	  
	  
	  /**
	   * ask for update of view callled at start
	   */
	  private void askForUpdate()
	  {
	    setChanged(); notifyObservers("START only"); // Notify
	  }

	  
	  /**
	   * Make a new Basket
	   * @return an instance of a new Basket
	   */
	  protected Basket makeBasket()
	  {
	    return new Basket();
	  }
	  
	  
	  /**
	   * make a Basket when required
	   */
	  private void makeBasketIfReq()
	  {
	    if ( theBasket == null )
	    {
	      try
	      {
	        int uon   = theOrder.uniqueNumber();     // Unique order num.
	        theBasket = makeBasket();                //  basket list
	        theBasket.setOrderNum( uon );            // Add an order number
	      } catch ( OrderException e )
	      {
	        DEBUG.error( "Comms failure\n" +
	                     "KioskModel.makeBasket()\n%s", e.getMessage() );
	      }
	    }
	  }
}
