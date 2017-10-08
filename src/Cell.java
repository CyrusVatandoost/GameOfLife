
public class Cell {

	private boolean alive;
	private int counter;
	
	public Cell() {
		this.alive = false;
		counter = 0;
	}
	
	public void update() {
		
		if(getState()) {
			
			if(counter == 2 || counter == 3)
				setState(true);
			else
				setState(false);
		}
		else {
			
			if(counter == 3)
				setState(true);
			else
				setState(false);
			
		}
		
	}
	
	public void setState(boolean alive) {
		this.alive = alive;
	}
	
	public void setState() {
		if(alive == true)
			alive = false;
		else
			alive = true;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public boolean getState() {
		return alive;
	}
	
	public int getCounter() {
		return counter;
	}
	
}
