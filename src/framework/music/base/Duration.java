package framework.music.base;

public class Duration{
	
	public static final int NOTE_DURATION_WHOLE = 1;
	public static final int NOTE_DURATION_HALF = 2;
	public static final int NOTE_DURATION_QUARTER = 4;
	public static final int NOTE_DURATION_EIGHTH = 8;
	public static final int NOTE_DURATION_SIXTEENTH = 16;
	public static final int NOTE_DURATION_THIRTY_SECOND = 32;
	
	private int length;
	private int start;
	
	public Duration(Time time, int length){
		this.setStart(time.getTime());
		this.length = length;
	}
	
	public Duration(int start, int length){
		this.setStart(start);
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
}
