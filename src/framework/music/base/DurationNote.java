package framework.music.base;

public class DurationNote extends Note implements Comparable<DurationNote>{

	private Duration duration;
	
	public DurationNote(int note, int start, int length, int velocity) {
		super(note, velocity);
		duration = new Duration(start, length);
	}

	public DurationNote(int note, int start, int length) {
		super(note);
		duration = new Duration(start, length);
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration (Duration duration) {
		this.duration = duration;
	}

	@Override
	public int compareTo(DurationNote durationNoteCompare) {

		if(durationNoteCompare.getDuration().getStart() == this.getDuration().getStart())
			return 0;
		else if(durationNoteCompare.getDuration().getStart() < this.getDuration().getStart())
			return 1;
		else if(durationNoteCompare.getDuration().getStart() > this.getDuration().getStart())
			return -1;
		else return 0;

	}
		
}