package framework.utilities;

import framework.music.player.Header;
import framework.music.player.TimeFrameChords;
import framework.music.player.TimeFrameNotes;

public class AJAMFile {

	
	//chord qualities
	public static final int CHORD_MAJOR = 'M';
	public static final int CHORD_MINOR = 'm';
	public static final int CHORD_DOMINANT = 'D';
	public static final int CHORD_DIMINISHED = 'd';
	public static final int CHORD_VELOCITY = 55;
	//note qualities
	public static final int NOTE_WHOLE = 'w';
	public static final int NOTE_HALF = 'h';
	public static final int NOTE_QUARTER = 'q';
	public static final int NOTE_EIGHTH = 'e';
	public static final int NOTE_SIXTEENTH = 's';
	public static final int NOTE_THIRTY_SECOND = 't';
	public static final int NOTE_REST = 'R';
	public static final int NOTE_TIE = 'T';
	public static final int MAX_NOTE_DEPTH = 32;
	public static final int INTONATION_NATURAL = 'n';
	public static final int INTONATION_SHARP = 's';
	public static final int INTONATION_FLAT = 'b';
	//separators
	static final char SEPARATOR_NOTE = ';';
	static final char SEPARATOR_SECTION = '\n';
	static final char SEPARATOR_HEADER = ';';

	TimeFrameNotes notes;
	TimeFrameChords chords;
	Header header;
	
	AJAMFile(){}
	
	void setNotes(TimeFrameNotes notes){
		this.notes = notes;
	}
	
	void setChords(TimeFrameChords chords){
		this.chords = chords;
	}
	
	void setHeader(Header header){
		this.header = header;
	}

	public TimeFrameNotes getNotes() {
		return notes;
	}

	public TimeFrameChords getChords() {
		return chords;
	}

	public Header getHeader() {
		return header;
	}
		
}