package clients.shopDisplay;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import clients.shopDisplay.DisplayMusic;

/**
 * JUnit Tests for the DisplayMusic class
 * Tests creating and playing of play list
 * Tests error handling
 * @author  Ali Trabelsi
 * @version 1.0
 */
class DisplayMusicTest {

	/**
	 * Tests if play list can be created, and the method is playing them in the correct order
	 */
	@Test
	void playlistTest() {
		DisplayMusic music = new DisplayMusic();	//instance of DisplayMusic

        music.playPlaylist();						//calls playPlaylist method
        
        music.playlist.put(1, "storeSong1.wav");
        music.playlist.put(2, "storeSong2.wav");
        music.playlist.put(3, "storeSong3.wav");
        music.playlist.put(4, "storeSong4.wav");
        music.playlist.put(5, "storeSong5.wav");
        music.playlist.put(6, "storeSong6.wav"); 	//populates play list
        
        assertTrue(music.isMusicPlaying());			//uses isMusicPlaying method to test if Clip is active
        
        assertEquals(1, music.getCounter());		//uses getCounter method to test if counter is at correct instance
	}
	
	/**
	 * Tests the methods error handling by purposely using a non-existent file name
	 */
	@Test
	void playlistErrorHandlingTest() {
		DisplayMusic music = new DisplayMusic();	//instance of DisplayMusic
		
		music.playlist.put(1, "incorrectFile.wav");	//adds incorrect file name to HashMap
		
		try {
            music.playPlaylist();					//calls playPlaylist method
        } catch (Exception e) {
            assertEquals("java.io.FileNotFoundException: incorrectFile.wav (No such file or directory)", e.getMessage().trim());	//compares error result to the incorrect file error
        }
	}

}
