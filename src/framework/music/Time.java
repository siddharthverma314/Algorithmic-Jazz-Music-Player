package framework.music;

public class Time {

	private int time;
	
	Time(){		
		time = 0;
	}
	
	Time(int start){
		time = start;
	}
	
	public void tick(){
		time++;
	}
	
	public int getTime(){
		return time;
	}
	
	public void setTime(int time){
		this.time = time;
	}
	
	//UPDATE THIS METHOD WHENEVER YOU ADD A NEW VARIABLE
	public void setTime(Time time){
		this.time = time.getTime();
	}
	
}
