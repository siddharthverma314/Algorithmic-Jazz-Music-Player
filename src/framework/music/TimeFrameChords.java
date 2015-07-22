package framework.music;

import java.util.List;
import java.util.ArrayList;

public class TimeFrameChords {

	private List<DurationChord> durationChords;
	private int length;
	private int count;
	
	public TimeFrameChords(){
		durationChords = new ArrayList<DurationChord>();
		length = 0;
		count = 0;
	}
	
	public int addChord(DurationChord chord){
		durationChords.add(chord);
		int end = chord.getDuration().getStart() + chord.getDuration().getLength();
		length = end > length ? end : length;	
		return count++;
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
	
	public DurationChord getChord(int index){
		return durationChords.get(index);
	}
	
	public void removeChord(int index){
		durationChords.remove(index);
		count--;
		recalculateLength();
	}
	
	public int getLength(){
		return length;
	}
	
	private void recalculateLength(){

		length = 0;
		
		for(DurationChord chord: durationChords){
			
			int end = chord.getDuration().getStart() + chord.getDuration().getLength();
			length = end > length ? end : length;
			
		}
		
	}

	
}
