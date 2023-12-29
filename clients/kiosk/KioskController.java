package clients.kiosk;

public class KioskController {
	private KioskModel model = null;
	private KioskView  view  = null;

	  /**
	   * Constructor
	   * @param model The model 
	   * @param view  The view from which the interaction came
	   */
	  public KioskController( KioskModel model, KioskView view )
	  {
	    this.view  = view;
	    this.model = model;
	  }
	  
	  /**
	   * Catalogue interaction from view
	   * @param pt The product type to be checked
	   */
	  public void doCatalogue (String pt) {
		  model.doCatalogue(pt);
	  }
	  
	  /**
	   * Check interaction from view
	   * @param pn The product number to be checked
	   */
	  public void doCheck( String pn)
	  {
	    model.doCheck(pn);
	  }
	  
	  /**
	   * Buy interaction from view
	   * @param pn Product number being brought
	   */
	  public void doBuy(String pn) {
		  model.doBuy(pn);
	  }
	  
	  /**
	   * Pay interaction from view
	   * @param cn Card Number
	   * @param ce	Card Expiration
	   * @param cvc	Card CVC
	   */
	  public void doPay(String cn, String mm, String yy, String cvc){
		  model.doPay(cn, mm, yy, cvc);
	  }

	  /**
	   * Clear interaction from view
	   */
	  public void doClear()
	  {
	    model.doClear();
	  }
}
