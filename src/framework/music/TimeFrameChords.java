package framework.music;

import java.util.List;
import java.util.ArrayList;

public class TimeFrameChords {

	private List<DurationChord> durationChords;
	
	public TimeFrameChords(){
		durationChords = new ArrayList<DurationChord>();
	}
	
	public void addChord(DurationChord chord){
		durationChords.add(chord);
	}
	
	public List<DurationChord> getChordsAtTime(Time time){
		
		List<DurationChord> chords = new ArrayList<DurationChord>();
		
		for(DurationChord chord : durationChords){
			
			if(chord.getDuration().getStart() <= time.getTime() && 
					chord.getDuration().getStart() + chord.getDuration().getLength() >= time.getTime())
				chords.add(chord);
			
		}
		
		return chords;
		
	}
	
}
