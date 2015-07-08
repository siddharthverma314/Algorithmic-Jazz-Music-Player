package player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import framework.utilities.AJAMDecoder;
import framework.utilities.TimeframePlayer;
import framework.utilities.AJAMDecoder.AJAMFileErrorException;

public class Display {
	
	public static void main(String args[]){
		
		AJAMDecoder decode = new AJAMDecoder();
		try {
			decode.decodeAJAM(new FileInputStream("res/how_high_the_moon.ajam"));
			TimeframePlayer player = new TimeframePlayer(decode.getBPM());
			player.addTimeFrame(decode.getMelodyNoteOn(), decode.getMelodyNoteOff());
			player.playTimeFrame();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AJAMFileErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
