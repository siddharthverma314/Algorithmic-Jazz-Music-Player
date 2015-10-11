package framework.music.player;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import framework.music.base.DurationChord;
import framework.music.base.DurationNote;
import framework.music.base.Note;
import framework.music.base.Time;

public class TimeFramePlayer {

	Synthesizer synth;
	private Header header;
	private int swing;
	private int length;
	
	MidiChannel piano;
	public final int piano_channel = 10;
	public final int drum_channel = 9;
	private final int total_channels = 15;
	
	List<TimeFrameChords>[] timeFrameChords;
	List<TimeFrameNotes>[] timeFrameNotes;

	@SuppressWarnings("unchecked")
	public TimeFramePlayer(Header header){
		this.header = header;
		swing = 1000;
		
		//init synth
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			piano = synth.getChannels()[piano_channel];
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			return;
		}
		
		//init everything else
		timeFrameChords = new ArrayList[total_channels];
		timeFrameNotes = new ArrayList[total_channels];
		
		for(int i = 0; i < total_channels; i++){
			timeFrameChords[i] = new ArrayList<>();
			timeFrameNotes[i] = new ArrayList<>();
		}
		
	}
	
	public void addTimeFrameChord(TimeFrameChords timeFrameChords, int instrument){
		this.timeFrameChords[instrument].add(timeFrameChords);
		length = timeFrameChords.getLength() > length ? timeFrameChords.getLength() : length;
	}
	
	public void addTimeFrameChord(TimeFrameChords timeFrameChords){
		this.addTimeFrameChord(timeFrameChords, piano_channel);
	}
	
	public void addTimeFrameNote(TimeFrameNotes timeFrameNotes, int instrument){
		this.timeFrameNotes[instrument].add(timeFrameNotes);
		length = timeFrameNotes.getLength() > length ? timeFrameNotes.getLength() : length;
	}
	
	public void addTimeFrameNote(TimeFrameNotes timeFrameNotes){
		this.addTimeFrameNote(timeFrameNotes, piano_channel);
	}
	
	public void playTimeFrame(){
		
		long prevTime = System.currentTimeMillis();
		Time time = new Time();
				
		for(int i = 0; i < length + 32; i++){
			
			//process
			for(int channel = 0; channel < total_channels; channel++){
				for(TimeFrameNotes t : timeFrameNotes[channel]){
					for(DurationNote n : t.getNotesAtTime(time)){
						if(time.getTime() == n.getDuration().getStart()){
							piano.noteOn(n.getMidi(), n.getVelocity());
						}else if(time.getTime() == n.getDuration().getStart() + n.getDuration().getLength() - 1){
							piano.noteOff(n.getMidi());
						}
					}
				}
				
				for(TimeFrameChords t : timeFrameChords[channel]){
					for (DurationChord c : t.getChordsAtTime(time)){
						if(time.getTime() == c.getDuration().getStart()){
							for (Note n : c.generateNoteArray()){
								piano.noteOn(n.getMidi(), n.getVelocity());
							}
						}else if(time.getTime() == c.getDuration().getStart() + c.getDuration().getLength() - 1){
							for (Note n : c.generateNoteArray()){
								piano.noteOff(n.getMidi());
							}
						}
					}
	
				}
			}
			
			time.tick();
			
			try {
				long to_sleep = (long) ((6000 + swing * Math.sin(Math.PI * i / 4.0))/header.getBeatsPerMinute());
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
