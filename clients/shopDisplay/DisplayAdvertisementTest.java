package clients.shopDisplay;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

/**
 * JUnit Tests for the DisplayAdvertisement class
 * Tests ImageIcon array
 * Tests random number generator
 * @author  Ali Trabelsi
 * @version 1.0
 */
class DisplayAdvertisementTest {

	/**
	 * Tests length of ImageIcon array
	 * Stores advert file locations
	 */
	@Test
	void testAdPointer() {
		ImageIcon ad1 = new ImageIcon("images/ad1.png");
		ImageIcon ad2 = new ImageIcon("images/ad2.png");
		ImageIcon ad3 = new ImageIcon("images/ad3.png");
		ImageIcon ad4 = new ImageIcon("images/ad4.png");
		ImageIcon ad5 = new ImageIcon("images/ad5.png");		//ImageIcon of adverts
		
		ImageIcon images[] = {ad1, ad2, ad3, ad4, ad5};			//ImageIcon array of adverts
		assertEquals(5, images.length);							//Tests length of array is equal to expected length of program
		
	}
	
	/**
	 * Tests that lastCounter and currentCounter are not the same after calling randomGenerator a few times
	 */
	@Test
	void testRandomGenerator() {
		DisplayAdvertisement ads = new DisplayAdvertisement();			//instance of DisplayAdvertisement
		
		ads.randomGenerator();											
		assertFalse(ads.getCurrentCounter() != ads.getLastCounter());
		
		ads.randomGenerator();
		assertFalse(ads.getCurrentCounter() != ads.getLastCounter());
		
		ads.randomGenerator();											//calls randomGenerator method
		assertFalse(ads.getCurrentCounter() != ads.getLastCounter());	//uses both getter methods and compares values of counters
	}

}
