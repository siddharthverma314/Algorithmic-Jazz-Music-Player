package framework.music;

import java.util.ArrayList;
import java.util.List;

import framework.utilities.ListUtils;

public class Chords {
	
	private int[] scale;
	private List<Integer> tones;
	private Note rootNote;
	
	public Chords(int[] scale, Note rootNote) {
		this.scale = scale;
		this.setRootNote(rootNote);
		tones = new ArrayList<Integer>();
		tones.add(1);
		tones.add(3);
		tones.add(5);
		tones.add(7);
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
		
		Note[] to_return = new Note[tones.size()];
		
		for(int i = 0; i < tones.size(); i++){
			
			to_return[i] = Scales.getInterval(rootNote, scale, tones.get(i));
			
		}
		
		return to_return;
		
	}
	
}
