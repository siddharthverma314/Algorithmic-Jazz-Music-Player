package framework.music;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class TimeFramePlayer {

	Synthesizer synth;
	private int BPM;
	private int swing;
	private int length;
	
	MidiChannel piano;
	
	List<TimeFrameChords> timeFrameChords;
	List<TimeFrameNotes> timeFrameNotes;

	public TimeFramePlayer(int BPM){
		
		this.BPM = BPM;
		swing = 50;
		
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
		length = timeFrameChords.getLength() > length ? timeFrameChords.getLength() : length;
	}
	
	public void addTimeFrameNote(TimeFrameNotes timeFrameNotes){
		this.timeFrameNotes.add(timeFrameNotes);
		length = timeFrameNotes.getLength() > length ? timeFrameNotes.getLength() : length;
	}
	
	public void playTimeFrame(){
		
		//TODO: add length stuff later
		long prevTime = System.currentTimeMillis();
		Time time = new Time();
				
		for(int i = 0; i < length + 32; i++){
			
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
