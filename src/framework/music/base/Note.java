package framework.music.base;

public class Note {
	
	private int midi, velocity;
	
	public int getMidi() {
		return midi;
	}

	public void setMidi(int midi) {
		this.midi = midi;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public Note(int note, int velocity) {
		this.midi = note;
		this.velocity = velocity;
	}
	
	public Note(int note) {
		this.midi = note;
		this.velocity = 100;
	}
	
	public void sharp(){
		this.midi++;
	}
	
	public void flat(){
		this.midi--;
	}
	
	public boolean equals(Note otherNote){
		return StandardNotes.noteEquals(this, otherNote);
	}
	
	public Note getNote(){
		return this;
	}

	public void setNote(Note note){
		
		this.midi = note.getMidi();
		this.velocity = note.getVelocity();
		
	}
	
	public void shiftInterval(int[] scale, int interval){
		
		if(interval > 15 || interval < 0)
			throw new IndexOutOfBoundsException("The interval reqested is beyond two octaves or is negative. Interval: "
		+ Integer.toString(interval));
		else if (interval > 7){
			incrementOctave();
			interval -= 8;
			this.midi += scale[interval];
		}else{
			this.midi += scale[interval];
		}
		
	}
	
	public void incrementOctave(){
		
		this.midi += Scales.OCTAVE;
		
	}
	
}