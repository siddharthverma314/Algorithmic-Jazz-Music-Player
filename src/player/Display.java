package player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import framework.music.improvisation.Pattern;
import framework.music.player.TimeFramePlayer;
import framework.utilities.AJAMDecoder;
import framework.utilities.AJAMFile;
import framework.utilities.AJAMFileErrorException;

public class Display {
	
	public static void main(String args[]){
		
		try {
			AJAMFile ajam = AJAMDecoder.decodeAJAM(new FileInputStream("res/how_high_the_moon.ajam"));
			TimeFramePlayer player = new TimeFramePlayer(ajam.getHeader());
			player.addTimeFrameNote(ajam.getNotes());
			player.addTimeFrameChord(Pattern.createChordPattern(Pattern.BOSSA_NOVA_1, ajam.getChords(), 32));
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
