package framework.music;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class TimeFramePlayer {
	
/**THIS IS THE OLD TIMEFRAME PLAYER
 	MidiChannel piano;

	Synthesizer synth;
	public int VELOCITY_PIANO = 100;
	
	ExtendableArray<int[]> note_ons;
	ExtendableArray<int[]> note_offs;
	int BPM;
	int swing;
	
	public TimeFramePlayer(int BPM){
		
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			piano = synth.getChannels()[0];
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
		note_ons = new ExtendableArray<int[]>();
		note_offs = new ExtendableArray<int[]>();
		this.BPM = BPM;
		this.swing = 2000;
		
	}
	
	public void addTimeFrame(ExtendableArray<Integer> note_on, ExtendableArray<Integer> note_off){
		
		for(int i = 0; i < (note_on.length() > note_off.length() ? note_on.length() : note_off.length()); i++){
			
			Integer to_put = note_on.get(i);
			int[] slot = note_ons.get(i);
			
			if(slot == null){
				slot = new int[10];
				if(to_put != null){
					slot[0] = to_put.intValue();
					for(int j = 1; j < slot.length; j++){
						slot[j] = 0;
					}
				}
			}else{
				for(int x : slot){
					if(x == 0){
						x = to_put;
						break;
					}
				}
			}
			
			note_ons.set(i, slot);
			
			to_put = note_off.get(i);
			slot = note_offs.get(i);
			
			if(slot == null){
				slot = new int[10];
				if(to_put != null){
					slot[0] = to_put.intValue();
					for(int j = 1; j < slot.length; j++){
						slot[j] = 0;
					}
				}
			}else{
				for(int x : slot){
					if(x == 0){
						x = to_put;
						break;
					}
				}
			}
			
			note_offs.set(i, slot);
			
		}
		
	}
	
	public void playTimeFrame(){
		
		for(int i = 0; i < (note_ons.length() > note_offs.length() ? note_ons.length() : note_offs.length()); i++){
			
			for(int j = 0; j < 10; j++){
				
				if(note_offs.get(i)[j] != 0)
					piano.noteOff(note_offs.get(i)[j]);
				if(note_ons.get(i)[j] != 0)
					piano.noteOn(note_ons.get(i)[j], VELOCITY_PIANO);
				
			}
			
			try {
				Thread.sleep((long) ((6000 + swing * Math.sin(Math.PI * i / 4.0))/BPM));
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void cleanUp(){
		
		synth.close();
		
	}*/

	// THIS IS THE NEW TIME FRAME PLAYER
	
	Synthesizer synth;
	int BPM;
	int swing;
	
	MidiChannel piano;
	
	List<TimeFrameChords> timeFrameChords;
	List<TimeFrameNotes> timeFrameNotes;

	public TimeFramePlayer(int BPM){
		
		this.BPM = BPM;
		swing = 0;
		
		//init synth
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			piano = synth.getChannels()[0];
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			return;
		}
		
		//init everything else
		timeFrameChords = new ArrayList<TimeFrameChords>();
		timeFrameNotes = new ArrayList<TimeFrameNotes>();
		
	}
	
	public void addTimeFrameChord(TimeFrameChords timeFrameChords){
		this.timeFrameChords.add(timeFrameChords);
	}
	
	public void addTimeFrameNote(TimeFrameNotes timeFrameNotes){
		this.timeFrameNotes.add(timeFrameNotes);
	}
	
	public void playTimeFrame(){
		
		//TODO: add length stuff later
		long prevTime = System.currentTimeMillis();
		Time time = new Time();
				
		for(int i = 0; i < 1000; i++){
			
			//process
			//TODO: Add Chords stuff
			for(TimeFrameNotes t : timeFrameNotes){
				
				for(DurationNote n : t.getNotesAtTime(time)){
										
					if(time.getTime() == n.getDuration().getStart()){
						piano.noteOn(n.getMidi(), n.getVelocity());
					}else if(time.getTime() == n.getDuration().getStart() + n.getDuration().getLength()){
						piano.noteOff(n.getMidi());
					}
					
				}
				
			}
			
			for(TimeFrameChords t : timeFrameChords){

				for (DurationChord c : t.getChordsAtTime(time)){

					if(time.getTime() == c.getDuration().getStart()){
						
						for (Note n : c.generateNoteArray()){
							
							piano.noteOn(n.getMidi(), n.getVelocity());
							
						}
						
					}else if(time.getTime() == c.getDuration().getStart() + c.getDuration().getLength()){
						
						for (Note n : c.generateNoteArray()){
							
							piano.noteOff(n.getMidi());
							
						}
						
					}

				}

			}
			
			time.tick();
			
			try {

				long to_sleep = (long) ((6000 + swing * Math.sin(Math.PI * i / 4.0))/BPM);
				to_sleep -= System.currentTimeMillis() - prevTime;
				Thread.sleep(to_sleep);
				prevTime = System.currentTimeMillis();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			
			
		}
		
	}
	
}
