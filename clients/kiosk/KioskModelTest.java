package clients.kiosk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit Test for the KioskModel class
 * Tests catalogue alias functionality
 * @author  Ali Trabelsi
 * @version 1.0
 */
class KioskModelTest {

	/**
	 * Tests the checkAlias method responds correctly to different queries
	 */
	@Test
	void testCatalogueAlias(){
		KioskModel kiosk = new KioskModel();	//New KioskModel object called kiosk
		kiosk.generateHashMap();				//Populates HashMap
		
		kiosk.pn = "tv";						
		assertEquals(true, kiosk.checkAlias());	
		
		kiosk.pn = "usb";
		assertEquals(true, kiosk.checkAlias());
		
		kiosk.pn = "kettle";
		assertEquals(false, kiosk.checkAlias());
		
		kiosk.pn = "lamp";
		assertEquals(false, kiosk.checkAlias()); //Tests product types on the HashMap
		
	}
	
	/**
	 * Tests error handling of different methods when theStock object isn't set up using the middle factory
	 */
	@Test
	void testErrorHandling(){
		KioskModel kiosk = new KioskModel();	//New KioskModel object called kiosk
		
		try {
			kiosk.doCatalogue("tv");			//Calls doCatalogue method
        } catch (Exception e) {
            assertEquals("Cannot invoke \"middle.StockReadWriter.getNumber(String)\" because \"this.theStock\" is null", e.getMessage().trim());	//compares error result to the expected error
        }
		
		try {
			kiosk.doPay("1111222233334444", "03", "28", "123" );	//Calls doPay method
        } catch (Exception e) {
            assertEquals("Cannot invoke \"middle.StockReadWriter.getNumber(String)\" because \"this.theStock\" is null", e.getMessage().trim());	//compares error result to the expected error
        }
	}
	
	
	

}
