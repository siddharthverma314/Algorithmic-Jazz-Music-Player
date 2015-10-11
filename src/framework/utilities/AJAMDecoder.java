package framework.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import framework.music.base.Chord;
import framework.music.base.Duration;
import framework.music.base.DurationChord;
import framework.music.base.DurationNote;
import framework.music.base.Note;
import framework.music.base.Scales;
import framework.music.base.StandardNotes;
import framework.music.player.Header;
import framework.music.player.TimeFrameChords;
import framework.music.player.TimeFrameNotes;
import framework.music.player.TimeFramePlayer;

public class AJAMDecoder {
	
	/**
	 * AJAM is my file type for this software. It's format is like this:
	 * AJAM consists of two parts: The chords and midi data.
	 * 
	 * 1. Chords:
	 * The chords are written in two parts: first the root note, and then a quality describing the chord. The root note
	 * is just the midi note (as a number) and the duration (see midi data). The qualities are written as follows:
	 * 		M - Major
	 * 		m - Minor
	 * 		d - Diminished
	 * 		D - Dominant
	 * 
	 * 2. Midi data: It's written in two parts - the midi note, a tie(T) or a rest(R), and then the duration for it. Velocity is not included.
	 * The duration is written as follows:
	 * 		w - Whole note
	 * 		h - Half note
	 * 		q - Quarter note
	 * 		e - Eighth note
	 * 		s - Sixteenth note
	 * 		t - Thirty-second note
	 * The note is written as follows:
	 * 		[A-G][1-9][nsf]
	 * Where
	 * 		A-G is the note name
	 * 		1-9 is the octave number.
	 * 		nsb is the intonation (natural, sharp, flat). 
	 * C4 is the middle C.
	 * 
	 * No support for triplets.
	 * 
	 * Each frame is separated by a ";"
	 * For chords, if the chord continues, in the slot, it is represented as "/"
	 * 
	 * There is a header which has the following info: Tempo, time signature (in order)
	 * 
	 * All the sections are separated by "\n"
	 * 
	 */

	public static AJAMFile decodeAJAM(InputStream in) throws AJAMFileErrorException{
		
		try {
			String[] data = separateSections(in);
			AJAMFile ajam = new AJAMFile();
			ajam.setHeader(parseHeader(data[0]));
			ajam.setChords(parseChords(data[1]));
			ajam.setNotes(parseMelody(data[2]));
			return ajam;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AJAMFileErrorException();
		}
		
	}
	
	private static String[] separateSections(InputStream in){
		Scanner s = new Scanner(in);
		s.useDelimiter(Character.toString(AJAMFile.SEPARATOR_SECTION));
		String[] data = new String[3];
		for(int i = 0; i < 3; i++){
			data[i] = s.next();
			data[i] = data[i].replace("\r", "");
			data[i] = data[i].replace("\n", "");
			data[i] += ";";
		}
		s.close();
		return data;
	}
	
	private static Header parseHeader(String data){
		//data = data.substring(0, data.length() - 1);		
		Scanner s = new Scanner(data);
		Header header = new Header();
		s.useDelimiter(Character.toString(AJAMFile.SEPARATOR_HEADER));
		header.setBeatsPerMinute(s.nextInt());
		header.setTimeSignature(new int[]{s.nextInt(), s.nextInt()});
		s.close();
		return header;
	}
	
	private static TimeFrameChords parseChords(String data){
		//format: [A-G][intonation][0-9][quality][duration] OR R[duration] OR T[duration]
		Scanner s = new Scanner(data);
		s.useDelimiter(Character.toString(AJAMFile.SEPARATOR_NOTE));
		TimeFrameChords timeFrameChords = new TimeFrameChords();
		int time = 0, prevIndex = 0;
		String bit;
		
		//Patterns
		String patternDuration = "[" + (char)AJAMFile.NOTE_WHOLE + (char)AJAMFile.NOTE_HALF +
				(char)AJAMFile.NOTE_QUARTER +(char)AJAMFile.NOTE_EIGHTH + (char)AJAMFile.NOTE_SIXTEENTH + 
				(char)AJAMFile.NOTE_THIRTY_SECOND + "]";
		String chordQualities = "[" + (char)AJAMFile.CHORD_MAJOR + (char)AJAMFile.CHORD_MINOR + (char)AJAMFile.CHORD_DIMINISHED
				+ (char)AJAMFile.CHORD_DOMINANT + "]";
		Pattern normalChord = Pattern.compile("([A-G][sb]?)(\\d)(" + chordQualities + ")(" + patternDuration + ")");
		Pattern rest = Pattern.compile("R(" + patternDuration + ")");
		Pattern tie = Pattern.compile("T(" + patternDuration + ")");
		
		while(s.hasNext()){
			bit = s.next();
			if(bit.charAt(0) == AJAMFile.NOTE_REST){
				Matcher match = rest.matcher(bit);
				match.find();
				int duration = getLength(match.group(1).charAt(0));
				time += duration;
			}else if(bit.charAt(0) == AJAMFile.NOTE_TIE){
				Matcher match = tie.matcher(bit);
				match.find();
				int duration = getLength(match.group(1).charAt(0));
				DurationChord prevChord = timeFrameChords.getChord(prevIndex);
				prevChord.setDuration(new Duration(
						prevChord.getDuration().getStart(), prevChord.getDuration().getLength() + duration));
				timeFrameChords.removeChord(prevIndex);
				prevIndex = timeFrameChords.addChord(prevChord);
				time += duration;
			}else{
				Matcher match = normalChord.matcher(bit);
				match.find();
				String noteName = match.group(1);
				int octave = Integer.parseInt(match.group(2));
				int duration = getLength(match.group(4).charAt(0));
				int[] scale = getQuality(match.group(3).charAt(0));
				int midi = StandardNotes.getMidiFromNoteName(noteName, octave);
				Note rootNote = new Note(midi, AJAMFile.CHORD_VELOCITY);
				Chord chord = new Chord(scale, rootNote);
				DurationChord durationChord = new DurationChord(chord, new Duration(time, duration));
				prevIndex = timeFrameChords.addChord(durationChord);
				time += duration;
			}
		}
		s.close();
		return timeFrameChords;

	}
	
	private static TimeFrameNotes parseMelody(String data){
		//format: [A-G][intonation][0-9][duration] OR R[duration] OR T[duration]
		Scanner s = new Scanner(data);
		s.useDelimiter(Character.toString(AJAMFile.SEPARATOR_NOTE));
		TimeFrameNotes timeFrameNotes = new TimeFrameNotes();
		int time = 0, prevIndex = 0;
		String bit;
		
		//Patterns
		String patternDuration = "[" + (char)AJAMFile.NOTE_WHOLE + (char)AJAMFile.NOTE_HALF +
				(char)AJAMFile.NOTE_QUARTER +(char)AJAMFile.NOTE_EIGHTH + (char)AJAMFile.NOTE_SIXTEENTH + 
				(char)AJAMFile.NOTE_THIRTY_SECOND + "]";
		Pattern normalNote = Pattern.compile("([A-G][sb]?)(\\d)(" + patternDuration + ")");
		Pattern rest = Pattern.compile("R(" + patternDuration + ")");
		Pattern tie = Pattern.compile("T(" + patternDuration + ")");
		
		while(s.hasNext()){
			bit = s.next();
			if(bit.charAt(0) == AJAMFile.NOTE_REST){
				Matcher match = rest.matcher(bit);
				match.find();
				int duration = getLength(match.group(1).charAt(0));
				time += duration;
			}else if(bit.charAt(0) == AJAMFile.NOTE_TIE){
				Matcher match = tie.matcher(bit);
				match.find();
				int duration = getLength(match.group(1).charAt(0));
				DurationNote prevNote = timeFrameNotes.getNote(prevIndex);
				prevNote.setDuration(new Duration(
						prevNote.getDuration().getStart(), prevNote.getDuration().getLength() + duration));
				timeFrameNotes.removeNote(prevIndex);
				prevIndex = timeFrameNotes.addNote(prevNote);
				time += duration;
			}else{
				Matcher match = normalNote.matcher(bit);
				match.find();
				String noteName = match.group(1);
				int octave = Integer.parseInt(match.group(2));
				int duration = getLength(match.group(3).charAt(0));
				int midi = StandardNotes.getMidiFromNoteName(noteName, octave);
				DurationNote note = new DurationNote(midi, time, duration);
				prevIndex = timeFrameNotes.addNote(note);
				time += duration;
			}
		}
		s.close();
		return timeFrameNotes;
	}
	
	private static int getLength(char c){
		
		int length = 0;
		
		switch(c){
		
		case(AJAMFile.NOTE_WHOLE):
			length = 1;
			break;
		case(AJAMFile.NOTE_HALF):
			length = 2;
			break;
		case(AJAMFile.NOTE_QUARTER):
			length = 4;
			break;
		case(AJAMFile.NOTE_EIGHTH):
			length = 8;
			break;
		case(AJAMFile.NOTE_SIXTEENTH):
			length = 16;
			break;
		case(AJAMFile.NOTE_THIRTY_SECOND):
			length = 32;
			break;
		
		}
		
		if(length == 0)
			return -1;
		return AJAMFile.MAX_NOTE_DEPTH / length;
		
	}
	
	private static int[] getQuality(char c){
		
		switch(c){
		
		case(AJAMFile.CHORD_MAJOR):
			return Scales.MAJOR_SCALE;
		case(AJAMFile.CHORD_MINOR):
			return Scales.NATURAL_MINOR_SCALE;
		case(AJAMFile.CHORD_DOMINANT):
			return Scales.DOMINANT_SCALE;
		case(AJAMFile.CHORD_DIMINISHED):
			return Scales.DOUBLE_DIMINISHED_SCALE;
		default:
			return null;
		
		}
		
	}
	
	public static void main(String args[]){
		
		try {
			AJAMFile howHighTheMoon = AJAMDecoder.decodeAJAM(new FileInputStream("res/how_high_the_moon_new.ajam"));
			TimeFramePlayer player = new TimeFramePlayer(howHighTheMoon.getHeader());
			player.addTimeFrameNote(howHighTheMoon.getNotes());
			player.addTimeFrameChord(howHighTheMoon.getChords());
			player.playTimeFrame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}