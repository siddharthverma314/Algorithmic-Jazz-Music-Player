package framework.music.improvisation;

import framework.music.base.Chord;
import framework.music.base.Duration;
import framework.music.base.DurationChord;
import framework.music.base.Time;
import framework.music.player.TimeFrameChords;


public class Pattern {
	
	//these are the patterns. If the integer is negative, it is a rest.
	public static final int[] BOSSA_NOVA_1 = new int[]{8,12,4,-4,8,8,4,8,8};
	
	public static TimeFrameChords createChordPattern(int[] pattern, TimeFrameChords chords, int start){
		
		int time = start;
		int index = 0;
		TimeFrameChords newChords = new TimeFrameChords();

		while(time < chords.getLength()){
			
			if(pattern[index] > 0){
				Duration duration = new Duration(time, pattern[index]);
				for(DurationChord chord : chords.getChordsAtTime(new Time(time))){
					Chord newChord = new Chord(chord.getScale(), chord.getRootNote());
					newChords.addChord(new DurationChord(newChord, duration));
				}
			}
			time += Math.abs(pattern[index]);
			index++;
			if(index >= pattern.length){
				index -= pattern.length;
			}
			
		}
		
		return newChords;
		
	}

}