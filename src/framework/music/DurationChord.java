package framework.music;

public class DurationChord extends Chord implements Comparable<DurationChord>{

	private Duration duration;
	
	public DurationChord (Chord chord, Duration duration){
		
		super(chord);
		this.setDuration(duration);
		
	}

	public Duration getDuration() {
		return duration;
	}


	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	@Override
	public int compareTo(DurationChord durationChordCompare) {

		if(durationChordCompare.getDuration().getStart() == this.getDuration().getStart())
			return 0;
		else if(durationChordCompare.getDuration().getStart() < this.getDuration().getStart())
			return 1;
		else if(durationChordCompare.getDuration().getStart() > this.getDuration().getStart())
			return -1;
		else return 0;

	}

}
