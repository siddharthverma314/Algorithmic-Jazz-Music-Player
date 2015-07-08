package framework.music;

public class StandardNotes {

	public static final int MIDDLE_OCTAVE = 4;
	
	public static final int NOTE_C = 60;
	public static final int NOTE_D = 62;
	public static final int NOTE_E = 64;
	public static final int NOTE_F = 65;
	public static final int NOTE_G = 67;
	public static final int NOTE_A = 69;
	public static final int NOTE_B = 71;
	
	public static final int NOTE_Cs = 61;
	public static final int NOTE_Ds = 63;
	public static final int NOTE_Es = 65;
	public static final int NOTE_Fs = 66;
	public static final int NOTE_Gs = 68;
	public static final int NOTE_As = 70;
	public static final int NOTE_Bs = 72;
	
	public static final int NOTE_Cb = 59;
	public static final int NOTE_Db = 61;
	public static final int NOTE_Eb = 63;
	public static final int NOTE_Fb = 64;
	public static final int NOTE_Gb = 66;
	public static final int NOTE_Ab = 68;
	public static final int NOTE_Bb = 70;
	
	public static boolean noteEquals(Note note1, Note note2){
		
		return noteMidiEquals(note1, note2.getMidi()); 
		
	}
	
	public static boolean noteMidiEquals(Note note, int midi){
		
		return ((midi - note.getMidi()) % 12) == 0;
		
	}
	
	public static String getStandardNoteNameSharp(Note note){
		
		if(StandardNotes.noteMidiEquals(note, NOTE_A)) return "A";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_B)) return "B";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_C)) return "C";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_D)) return "D";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_E)) return "E";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_F)) return "F";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_G)) return "G";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_As)) return "As";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Cs)) return "Cs";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Ds)) return "Ds";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Fs)) return "Fs";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Gs)) return "Gs";
		else return null;
		
		
	}
	
	public static String getStandardNoteNameFlat(Note note){
		
		if(StandardNotes.noteMidiEquals(note, NOTE_A)) return "A";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_B)) return "B";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_C)) return "C";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_D)) return "D";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_E)) return "E";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_F)) return "F";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_G)) return "G";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Bb)) return "Bb";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Db)) return "Db";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Eb)) return "Eb";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Gb)) return "Gb";
		else if(StandardNotes.noteMidiEquals(note, StandardNotes.NOTE_Ab)) return "Ab";
		else return null;
		
		
	}
	
	public static int getMidiFromNoteName(String name, int octave){
		
		int shift = octave - 4;
		
		switch(name){
		
		case "C":
			return NOTE_C + shift;
		case "Cs":
			return NOTE_Cs + shift;
		case "Db":
			return NOTE_Db + shift;
		case "D":
			return NOTE_D + shift;
		case "Ds":
			return NOTE_Ds + shift;
		case "Eb":
			return NOTE_Eb + shift;
		case "E":
			return NOTE_E + shift;
		case "F":
			return NOTE_F + shift;
		case "Fs":
			return NOTE_Fs + shift;
		case "Gb":
			return NOTE_Gb + shift;
		case "G":
			return NOTE_G + shift;
		case "Gs":
			return NOTE_Gs + shift;
		case "Ab":
			return NOTE_Ab + shift;
		case "A":
			return NOTE_A + shift;
		case "As":
			return NOTE_As + shift;
		case "Bb":
			return NOTE_Bb + shift;
		case "B":
			return NOTE_B + shift;
		case "Bs":
			return NOTE_Bs + shift;
		case "Cb":
			return NOTE_Cb + shift;
		default:
			return 0;
		
		}
		
	}

	public static int getMidiFromNoteName(String name){
		return getMidiFromNoteName(name, 4);
	}
}
