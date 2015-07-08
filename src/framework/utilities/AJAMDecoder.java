package framework.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

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
	 * No support for triplets.
	 * 
	 * Each time frame is separated by a ";"
	 * For chords, if the chord continues, in the slot, it is represented as "/"
	 * 
	 * There is a header which has the following info: Tempo, time signature (in order)
	 * 
	 * All the sections are separated by "\n"
	 * 
	 */
	
	//chord qualities
	public static final int CHORD_MAJOR = 'M';
	public static final int CHORD_MINOR = 'm';
	public static final int CHORD_DOMINANT = 'D';
	public static final int CHORD_DIMINISHED = 'd';
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
	//separators
	final char SEPARATOR_NOTE = ';';
	final char SEPARATOR_SECTION = '\n';
	final char SEPARATOR_HEADER = ';';
	
	ExtendableArray<Integer> melody_note_on;
	ExtendableArray<Integer> melody_note_off;
	ExtendableArray<String[]> chords;
	int BPM;
	int[] timeSignature;
	
	//constructor - put stuff here if necessary later
	public AJAMDecoder(){
		
		
	}
	
	public int[] getTimeSignature(){
		return timeSignature;
	}
	
	public ExtendableArray<Integer> getMelodyNoteOn(){
		return melody_note_on;
	}
	
	public ExtendableArray<Integer> getMelodyNoteOff(){
		return melody_note_off;
	}
	
	public int getBPM(){
		
		return BPM;
		
	}
	
	public void decodeAJAM(InputStream in) throws AJAMFileErrorException{
		
		try {
			
			//get header, chords, melody
			String data[] = getSections(in);
			
			//parse header
			parseHeader(data[0]);
			
			//parse chords
			parseChords(data[1]);
			
			//parse melody
			parseMelody(data[2]);
			
		} catch (Exception e) {
			throw new AJAMFileErrorException();
		}
		
		//get BPM, tempo from header
		
	}
	
	private String[] getSections(InputStream in) throws IOException{
		
		String data[] = new String[3];
		data[0] = data[1] = data[2] = "";
		int count = 0;
		
		while (true){
			
			int b = in.read();
			
			if(b == -1) break;
			
			switch((char)b){
			
				case SEPARATOR_SECTION:
					++count;
					break;
				default:
					data[count] += Character.toString((char)b);
					break;
			
			}
			
		}//got header, chords, melody
		
		return data;
		
	}
	
	private void parseHeader(String header){
		
		int temp = 0; int count = 0;
		timeSignature = new int[2];
		for(int i = 0; i < header.length(); i++){
			
			char c = header.charAt(i);
			

			if(i == header.length() - 1){
				
				temp *= 10;
				temp += Character.getNumericValue(c);
				timeSignature[1] = temp;
				
			}else if(Character.isDigit(c)){
				temp *= 10;
				temp += Character.getNumericValue(c);
			}else if(c == SEPARATOR_HEADER){
				
				if(count == 0){
					BPM = temp;
				}else if(count == 1){
					timeSignature[0] = temp;
				}else if(count == 2){
					timeSignature[1] = temp;
				}
				
				temp = 0;
				++count;
				
			}
			
		}//header parsed
		
	}
	
	private void parseChords(String chords){
		
		
		
	}
	
	private int getLength(char c){
		
		switch((int) c){
		
		case(NOTE_WHOLE):
			return 1;
		case(NOTE_HALF):
			return 2;
		case(NOTE_QUARTER):
			return 4;
		case(NOTE_EIGHTH):
			return 8;
		case(NOTE_SIXTEENTH):
			return 16;
		case(NOTE_THIRTY_SECOND):
			return 32;
		default:
			return -1;
		
		}
		
	}
	
	private void parseMelody(String melody){
		
		//initialize the arrays
		melody_note_on = new ExtendableArray<Integer>();
		melody_note_off = new ExtendableArray<Integer>();
		
		//open scanner
		melody = melody + SEPARATOR_NOTE;
		Scanner s = new Scanner(melody);
		s.useDelimiter(Character.toString(SEPARATOR_NOTE));
		
		//parse it
		int timeframe = 0;
		int prevMidi = 0;
		while (s.hasNext()) {
			String bit = s.next();
			int midi = 0;
			int type = 0;
			for (char c : bit.toCharArray()) {
				
				//add midi data
				if(Character.isDigit(c)){
					type = 0;
					midi *= 10;
					midi += Character.getNumericValue(c);
				}
				
				//put rest
				else if(NOTE_REST == (int) c)
					type = 1;
				
				//check tie
				else if(NOTE_TIE == (int) c)
					type = 2;
				
				//put note
				else{

					int length = getLength(c);
					if(type == 0){
						melody_note_on.set(timeframe, midi);
						melody_note_off.set(timeframe + MAX_NOTE_DEPTH/length, midi);
					}
					else if(type == 2){
						melody_note_off.remove(timeframe);
						melody_note_off.set(timeframe + MAX_NOTE_DEPTH/length, prevMidi);
					}
					timeframe += MAX_NOTE_DEPTH/length;
					
				}
				
				if(type == 0)
					prevMidi = midi;
				
			}
		}
		
		//close scanner
		s.close();
		
	}
	
	public class AJAMFileErrorException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 5420901959461901867L;
		
	}
	
	public static void main(String args[]){
		
		AJAMDecoder d = new AJAMDecoder();
		try {
			d.decodeAJAM(new FileInputStream("res/how_high_the_moon.ajam"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (AJAMFileErrorException e1) {
			e1.printStackTrace();
		}
		for(int i = 0; i < d.melody_note_off.length(); i++){
			
			try {
				if(d.melody_note_on.get(i) == null)
					System.out.print("0 ");
				else
					System.out.print(d.melody_note_on.get(i) + " ");
			} catch (Exception e) {
				System.out.print("0 ");
			}
			
			if(d.melody_note_off.get(i) == null)
				System.out.print("0");
			else
				System.out.print(d.melody_note_off.get(i).intValue());
			
			System.out.println();
			
		}
		
	}
	
}
