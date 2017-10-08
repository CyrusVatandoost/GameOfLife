import java.util.concurrent.ThreadLocalRandom;

public class Map {

	private Cell[][] cellMap;
	private Cell[][] pastCellMap;
	private int height;
	private int width;
	private int mode; //0 - closed map, 1 - vertical wrap, 2 - horizontal wrap, 3 - both wrap
	private boolean play;
	private int waitTime;
	private boolean go;
	
	public Map(int height, int width) {
		setMap(height, width);
		mode = 0;
		play = true;
		waitTime = 100;
		go = true;
	}
	
	public void createMap() {
		int x, y;
		cellMap = new Cell[height][width];
		pastCellMap = new Cell[height][width];
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				cellMap[y][x] = new Cell();
				pastCellMap[y][x] = new Cell();
			}
		}
	}
	
	public void createMap(int size) {
		int x, y;
		
		height = 9 * size;
		width = 16 * size;
		
		cellMap = new Cell[height][width];
		pastCellMap = new Cell[height][width];
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				cellMap[y][x] = new Cell();
				pastCellMap[y][x] = new Cell();
			}
		}
	}
	
	public void updateMap() {
		int x, y;
	
		while(play) {

			countAllNeighbors();
			
			wait(waitTime);
			pastCellMap = cellMap;
			
			for(y = 0; y < height; y++) {
				for(x = 0; x < width; x++) {
					if(go)
						cellMap[y][x].update();
				}
			}
			
		}
	}
	
	public void nextStep() {
		int x, y;

		countAllNeighbors();
		
		pastCellMap = cellMap;
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
					cellMap[y][x].update();
			}
		}
				
	}
	
	public void randomMap() {
		int x, y;
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				cellMap[y][x].setState(getRandomBoolean());
			}
		}
		
		countAllNeighbors();
		pastCellMap = cellMap;
	}
	
	public void countAllNeighbors() {
		int x, y;
		
		pastCellMap = cellMap;
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				cellMap[y][x].setCounter(countNeighbors(y, x));
			}
		}
	}

	public void wait(int miliseconds) {
		//1000 milliseconds is equal to one second.
		try {
			Thread.sleep(miliseconds);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void togglePlayPause() {
		if(go)
			go = false;
		else
			go = true;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public void setMap(int height, int width) {
		this.height = height;
		this.width = width;
		createMap();
	}
	
	public boolean[] checkNeighbors(int y, int x) {
		boolean[] neighbors = new boolean[8];
		
		for(int i = 0; i < neighbors.length; i++) {
			neighbors[i] = false;
		}
		
		switch(mode) {
		
		// 0 1 2
		// 3 x 4
		// 5 6 7
		
		case 0:
	
			// 0 0 0
			// 0 A 0
			// 0 0 0

			// 1 0 0  1 1 1  1 0 0
			// 1 B 0  1 C 0  1 D 0
			// 1 0 0  1 0 0  1 1 1

			// 0 0 1  1 1 1  0 0 1
			// 0 E 1  0 F 1  0 G 1
			// 0 0 1  0 0 1  1 1 1
			
			// 1 1 1  0 0 0
			// 0 H 0  0 I 0
			// 0 0 0  1 1 1
			
			//
			
			// 1 1 1
			// 1 C 0
			// 1 0 0
			
			if(y == 0 && x == 0) {
				neighbors[0] = false;
				neighbors[1] = false;
				neighbors[2] = false;
				neighbors[3] = false;
				neighbors[4] = getPastCellState(y, x, 4);
				neighbors[5] = false;
				neighbors[6] = getPastCellState(y, x, 6);
				neighbors[7] = getPastCellState(y, x, 7);
			}
			
			// 1 0 0
			// 1 D 0
			// 1 1 1
			
			else if(y == height - 1 &&  x == 0) {
				neighbors[0] = false;
				neighbors[1] = getPastCellState(y, x, 1);
				neighbors[2] = getPastCellState(y, x, 2);
				neighbors[3] = false;
				neighbors[4] = getPastCellState(y, x, 4);
				neighbors[5] = false;
				neighbors[6] = false;
				neighbors[7] = false;
			}
			
			//	1 0 0
			//	1 B 0
			//	1 0 0
			
			else if(x == 0 && y > 0 && y < height - 1) {
				neighbors[0] = false;
				neighbors[1] = getPastCellState(y, x, 1);
				neighbors[2] = getPastCellState(y, x, 2);
				neighbors[3] = false;
				neighbors[4] = getPastCellState(y, x, 4);
				neighbors[5] = false;
				neighbors[6] = getPastCellState(y, x, 6);
				neighbors[7] = getPastCellState(y, x, 7);
			}
		
			//	1 1 1
			//	0 F 1
			//	0 0 1 
			
			else if(x == width - 1 && y == 0) {
				neighbors[0] = false;
				neighbors[1] = false;
				neighbors[2] = false;
				neighbors[3] = getPastCellState(y, x, 3);
				neighbors[4] = false;
				neighbors[5] = getPastCellState(y, x, 5);
				neighbors[6] = getPastCellState(y, x, 6);
				neighbors[7] = false;
			}
			
			// 0 0 1
			// 0 G 1
			// 1 1 1
			
			else if(x == width - 1 && y == height - 1) {
				neighbors[0] = getPastCellState(y, x, 0);
				neighbors[1] = getPastCellState(y, x, 1);
				neighbors[2] = false;
				neighbors[3] = getPastCellState(y, x, 3);
				neighbors[4] = false;
				neighbors[5] = false;
				neighbors[6] = false;
				neighbors[7] = false;
			}
			
			//	0 0 1
			//	0 E 1
			//	0 0 1
			
			else if(x == width - 1 && y > 0 && y < height - 1){
				neighbors[0] = getPastCellState(y, x, 0);
				neighbors[1] = getPastCellState(y, x, 1);
				neighbors[2] = false;
				neighbors[3] = getPastCellState(y, x, 3);
				neighbors[4] = false;
				neighbors[5] = getPastCellState(y, x, 5);
				neighbors[6] = getPastCellState(y, x, 6);
				neighbors[7] = false;
			}
	
			
			// 1 1 1
			// 0 H 0
			// 0 0 0
			
			else if(x > 0 && x < width - 1 && y == 0) {
				neighbors[0] = false;
				neighbors[1] = false;
				neighbors[2] = false;
				neighbors[3] = getPastCellState(y, x, 3);
				neighbors[4] = getPastCellState(y, x, 4);
				neighbors[5] = getPastCellState(y, x, 5);
				neighbors[6] = getPastCellState(y, x, 6);
				neighbors[7] = getPastCellState(y, x, 7);
			}
			
			// 0 0 0
			// 0 I 0
			// 1 1 1
			
			else if(x > 0 && x < width - 1 && y == height - 1) {
				neighbors[0] = getPastCellState(y, x, 0);
				neighbors[1] = getPastCellState(y, x, 1);
				neighbors[2] = getPastCellState(y, x, 2);
				neighbors[3] = getPastCellState(y, x, 3);
				neighbors[4] = getPastCellState(y, x, 4);
				neighbors[5] = false;
				neighbors[6] = false;
				neighbors[7] = false;
			}
			
			// 0 0 0
			// 0 A 0 
			// 0 0 0
			
			else if(x > 0 && x < width - 1 && y > 0 && y < height - 1){
				neighbors[0] = getPastCellState(y, x, 0);
				neighbors[1] = getPastCellState(y, x, 1);
				neighbors[2] = getPastCellState(y, x, 2);
				neighbors[3] = getPastCellState(y, x, 3);
				neighbors[4] = getPastCellState(y, x, 4);
				neighbors[5] = getPastCellState(y, x, 5);
				neighbors[6] = getPastCellState(y, x, 6);
				neighbors[7] = getPastCellState(y, x, 7);
			}
			
			break;
			
		case 1:
			
			break;
			
		case 2:
			
			break;
			
		case 3:
			
			break;
		
		}
		
		return neighbors;
	}
	
	public int countNeighbors(int y, int x) {
		int counter = 0;
		boolean[] neighbors = checkNeighbors(y, x);
		
		for(int i = 0; i < neighbors.length; i++) {
			if(neighbors[i])
				counter++;
		}
		
		return counter;
	}
	
	public Cell getPastCell(int y, int x) {
		return pastCellMap[y][x];
	}
	
	public boolean getPastCellState(int y, int x, int i) {
		
		boolean value = false;
		
		switch(i) {
		
		case 0:
			
			if(getPastCell(y - 1, x - 1).getState())
				value = true;
			
			break;
			
		case 1:
			
			if(getPastCell(y - 1, x).getState())
				value = true;
			
			break;
			
		case 2:
			
			if(getPastCell(y - 1, x + 1).getState())
				value = true;
			
			break;
			
		case 3:
			
			if(getPastCell(y, x - 1).getState())
				value = true;
			
			break;
			
		case 4:
			
			if(getPastCell(y, x + 1).getState())
				value = true;
			
			break;
			
		case 5:
			
			if(getPastCell(y + 1, x - 1).getState())
				value = true;
			
			break;
			
		case 6:
			
			if(getPastCell(y + 1, x).getState())
				value = true;
			
			break;
			
		case 7:
			
			if(getPastCell(y + 1, x + 1).getState())
				value = true;
			
			break;
		
		}
		
		return value;
	}
	
	public Cell getCell(int y, int x) {
		return cellMap[y][x];
	}
	
	public Cell[][] getCellMap() {
		return cellMap;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public boolean getRandomBoolean() {
		int rand;
		
		rand = ThreadLocalRandom.current().nextInt(1, 6);
		
		switch(rand) {
		case 1:
			return false;
		case 2:
			return false;
		case 3:
			return false;
		case 4:
			return false;
		default:
			return true;
		}
	}
}
