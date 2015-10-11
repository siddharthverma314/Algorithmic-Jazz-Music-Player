package framework.music.player;

import java.util.ArrayList;
import java.util.List;

import framework.music.base.Duration;
import framework.music.base.DurationNote;
import framework.music.base.Time;

public class NoteLooper extends TimeFrameNotes{

	//this class just loops a sequence for a particular duration
	
	TimeFrameNotes notes;
	Duration duration;
	
	//getters and setters
	public TimeFrameNotes getNotes() {
		return notes;
	}
	public void setNotes(TimeFrameNotes notes) {
		this.notes = notes;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	@Override
	public List<DurationNote> getNotesAtTime(Time t){
		
		int time = t.getTime();
		if(time > duration.getStart() && time < duration.getStart() + duration.getLength()){
			time = time % super.getLength();
			t = new Time();
			t.setTime(time);
			return super.getNotesAtTime(t);
		}else{
			return new ArrayList<DurationNote>();
		}
		
	}
	
}
