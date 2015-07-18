package framework.music;

public class Header {

	private int beatsPerMinute;
	private int[] timeSignature;
	
	public Header(){}
	
	public int[] getTimeSignature() {
		return timeSignature;
	}
	
	public void setTimeSignature(int[] timeSignature) {
		this.timeSignature = timeSignature;
	}
	
	public int getBeatsPerMinute() {
		return beatsPerMinute;
	}
	
	public void setBeatsPerMinute(int tempo) {
		this.beatsPerMinute = tempo;
	}
	
}
