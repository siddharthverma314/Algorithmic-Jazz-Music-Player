package framework.utilities;

public class ExtendableArray<E>{
	
	private E data[];
	
	@SuppressWarnings("unchecked")
	public ExtendableArray(){
		
		data = (E[]) new Object[1];
		
	}
	
	@SuppressWarnings("unchecked")
	public void set(int index, E element) throws IndexOutOfBoundsException{
		
		if(index < 0)
			throw new IndexOutOfBoundsException();
		else{
			
			if(index < data.length){
				
				data[index] = element;
				
			}else{
				
				E[] temp = (E[]) new Object[data.length];
				
				for(int i = 0; i < temp.length; i++){
					
					temp[i] = data[i];
					data[i] = null;
					
				}
				
				data = (E[])new Object[index + 1];
				
				for(int i = 0; i < temp.length; i++){
					
					data[i] = temp[i];
					temp[i] = null;
					
				}
				
				temp = null;
				data[index] = element;
				
			}
			
		}
		
	}
	
	public void remove(int index) throws ArrayIndexOutOfBoundsException{
		
		if (index >= data.length || index <= 0){
			throw new ArrayIndexOutOfBoundsException(index);
		}else{
			
			data[index] = null;
			
		}
		
	}
	
	public E[] getData(){

		return data;
		
	}
	
	public E get(int index){
		
		if(index >= data.length)
			return null;
		else if(index < 0)
			throw new ArrayIndexOutOfBoundsException(index);
		return data[index];
		
	}

	public int length(){
		
		return data.length;
		
	}
	
	
}