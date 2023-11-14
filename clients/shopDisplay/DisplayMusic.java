package clients.shopDisplay;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * The music Class that creates a play list and plays it in store
 * @author  Ali Trabelsi
 * @version 1.0
 */
public class DisplayMusic {
	private Clip clip; //instance of Clip class
    private AudioInputStream sound; //instance of AudioInputStream
    private int counter = 1; //sets counter variable to 1
    
    protected HashMap<Integer, String> playlist = new HashMap<Integer, String>(); //creates HashMap, mapping a integer number to a song name
    
    /**
     * Populates the HashMap with the different songs to create a play list,
     * and iterates through the songs creating a loop.
     */
    protected void playPlaylist(){ //playPlaylist method
  	  playlist.put(1, "storeSong1.wav");
  	  playlist.put(2, "storeSong2.wav");
  	  playlist.put(3, "storeSong3.wav");
  	  playlist.put(4, "storeSong4.wav");
  	  playlist.put(5, "storeSong5.wav");
  	  playlist.put(6, "storeSong6.wav"); //populates HashMap with song names located in CatShop folder
  	  
  	  try{ 
  		File file = new File(playlist.get(counter)); //creates instance of file and sets it to the next song in playlist
          sound = AudioSystem.getAudioInputStream(file); //sets the AudioInputStream to the currently selected song in file
          clip = AudioSystem.getClip(); //sets clip instance to create a clip
          clip.open(sound); //opens a clip and sets it to current music file
          clip.start(); //starts the clip - music plays
          
          clip.addLineListener(new LineListener(){ //adds line event listener to the clip, when clip ends function update called
          	public void update(LineEvent evt){ //update function
          		if(evt.getType() == LineEvent.Type.STOP){ //if the clip has stopped
          			if(counter == 6){ //if counter is 6 (if play list is at the end)
          				counter = 1; //sets counter to 1 (beginning of play list)
          			}
          			
          			else{ //if counter is less than 6 (play list not at end)
          				counter++; //increments counter by 1
          				playPlaylist(); //calls playPlaylist method - recursion
          			}
          		}
          	}
          });
  	  }
        catch(Exception e){
        	e.printStackTrace(); //prints error, if any thrown
        }
    }
    
    /**
     * Returns true if a song is currently being played
     * @return True/False value
     */
    protected boolean isMusicPlaying() { //method used by JUnit test
  	  if(clip.isOpen()) {
  		  return true;
  	  }
  	  
  	  else {
  		  return false;
  	  }
    }
    
    /**
     * Returns the current value of the counter
     * @return Counter
     */
    protected int getCounter() {
  	  return counter;
    }
    
}
