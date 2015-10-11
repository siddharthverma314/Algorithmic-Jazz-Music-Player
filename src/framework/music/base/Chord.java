package framework.music.base;

import java.util.ArrayList;
import java.util.List;

import framework.utilities.ListUtils;

public class Chord {
	
	private int[] scale;
	private List<Integer> tones;
	private Note rootNote;
	private Note[] generatedNotes;
	
	public Chord(int[] scale, Note rootNote) {
		this.scale = scale;
		this.setRootNote(rootNote);
		tones = new ArrayList<Integer>();
		tones.add(1);
		tones.add(3);
		tones.add(5);
		tones.add(7);
	}

	public Chord(Chord chord){
		
		this.scale = chord.scale;	
		this.tones = chord.tones;
		this.rootNote = chord.rootNote;
		
	}
	
	public int[] getTones() {
		return ListUtils.convertToIntArray(tones);
	}

	public void setTones(List<Integer> tones) {
		this.tones = tones;
	}

	public int[] getScale() {
		return scale;
	}
	
	public void setScale(int[] scale){
		this.scale = scale;
	}
	
	public void addTone(int intonation){
		
		if(tones.contains(new Integer(intonation)))
			;
		else
			tones.add(new Integer(intonation));
		
	}

	public Note getRootNote() {
		return rootNote;
	}
	
	public void setRootNote(Note rootNote) {
		this.rootNote = rootNote;
	}
	
	public Note[] generateNoteArray(){
		
		if(generatedNotes == null){
			generatedNotes = new Note[tones.size()];
			for(int i = 0; i < tones.size(); i++){
				generatedNotes[i] = Scales.getInterval(rootNote, scale, tones.get(i));
			}
		}

		return generatedNotes;
		
		
	}
	
	public int[] getMidi(){
		
		int[] midiArray = new int[tones.size()];
		
		for (int i = 0; i < tones.size(); i++){
			
			midiArray[i] = Scales.getInterval(rootNote, scale, tones.get(i)).getMidi();
			
		}
		
		return midiArray;
		
	}
	
	public Note[] getNotes(){
		
		Note[] noteArray = new Note[tones.size()];
		
		for (int i = 0; i < tones.size(); i++){
			
			noteArray[i] = Scales.getInterval(rootNote, scale, tones.get(i));
			
		}
		
		return noteArray;
		
	}
	
}
