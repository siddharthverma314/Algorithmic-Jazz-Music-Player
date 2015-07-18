package framework.music;

public class Scales {

	public static final int[] MAJOR_SCALE = {0, 2, 4, 5, 7, 9, 11};
	public static final int[] NATURAL_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 10};
	public static final int[] HARMONIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 11};
	public static final int[] MELODIC_MINOR_SCALE = {0, 2, 3, 5, 7, 9, 11};
	public static final int[] LYDIAN_SCALE = {0, 2, 4, 6, 7, 9, 11};
	public static final int[] DOMINANT_SCALE = {0, 2, 4, 5, 7, 9, 10};
	public static final int[] LYDIAN_DOMINANT_SCALE = {0, 2, 4, 6, 7, 9, 10};
	public static final int[] DOUBLE_DIMINISHED_SCALE = {0, 1, 3, 4, 6, 7, 9, 10};
	
	public static final int OCTAVE = 12;
	public static final int SHARP = 1;
	public static final int FLAT = -1;
	
	public static Note getInterval(Note note, int[] scale, int interval){
		
		return getInterval(note, scale, interval, 0);
		
	}
	
	public static Note getInterval(Note note, int[] scale, int interval, int intonation){
		
		Note to_return;

		if(interval > 15 || interval < 0)
			throw new IndexOutOfBoundsException("The interval reqested is beyond two octaves or is negative. Interval: "
		+ Integer.toString(interval));
		else if (interval > 7){
			note.incrementOctave();
			interval -= 8;
			to_return = new Note(note.getMidi() + scale[interval - 1], note.getVelocity());
		}else{
			to_return = new Note(note.getMidi() + scale[interval - 1], note.getVelocity());
		}
		
		if(intonation == Scales.SHARP)
			to_return.sharp();
		else if(intonation == Scales.FLAT)
			to_return.flat();
		return to_return;
		
	}
	
}