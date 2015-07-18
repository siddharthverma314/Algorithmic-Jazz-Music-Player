package framework.music;

import java.util.ArrayList;
import java.util.List;

public class TimeFrameNotes{

	private List<DurationNote> durationNotes;
	int count;
	
	public TimeFrameNotes(){
		durationNotes = new ArrayList<DurationNote>();
		count = 0;
	}
	
	public int addNote(DurationNote note){
		durationNotes.add(note);
		return count++;
	}
	
	public DurationNote getNote(int index){
		return durationNotes.get(index);
	}
	
	public void removeNote(int index){
		durationNotes.remove(index);
	}
	
	public List<DurationNote> getNotesAtTime(Time time){
		
		List<DurationNote> notes = new ArrayList<DurationNote>();
		
		for(DurationNote note : durationNotes){
			
			if(note.getDuration().getStart() <= time.getTime() && 
					note.getDuration().getStart() + note.getDuration().getLength() >= time.getTime())
				notes.add(note);
			
		}
		
		return notes;
		
	}

}
