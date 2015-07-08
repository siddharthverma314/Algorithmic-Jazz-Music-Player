package framework.utilities;
import javax.sound.midi.*;

public class TimeframePlayer {
	
	MidiChannel piano;
	Synthesizer synth;
	public int VELOCITY_PIANO = 100;
	
	ExtendableArray<int[]> note_ons;
	ExtendableArray<int[]> note_offs;
	int BPM;
	int swing;
	
	public TimeframePlayer(int BPM){
		
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
		
	}

}
