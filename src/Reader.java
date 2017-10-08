import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Reader {
	
	private Map map;
	private String fileName;
	
	public Reader(String fileName) {
		this.fileName = fileName;
		read();
	}
	
	public void read() {
		File file = new File(fileName);
		String line;
		int width, height, i, x, y;

		try {
			
			Scanner s = new Scanner(file);
			
			width = Integer.parseInt(s.nextLine());
			height = Integer.parseInt(s.nextLine());
			
			map = new Map(height, width);
			
			for(y = 0; y < height; y++) {
				
				line = s.nextLine();
				
				for(x = 0; x < line.length(); x++) {
					i = Character.getNumericValue(line.charAt(x));
					
					if(i == 1)
						map.getCell(y, x).setState(true);
					else
						map.getCell(y, x).setState(false);
					
				}
				
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
	}
	
	public Map getMap() {
		return map;
	}
}
