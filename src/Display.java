import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Display extends JComponent {
	
	private Map map;
	
	public Display(Map map) {
		
		this.map = map;
		
        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    repaint();
                    try {Thread.sleep(10);} catch (Exception ex) {}
                }
            }
        });
        animationThread.start();
	}
	
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		
		Color DARKGREY = new Color(64, 64, 64);
		
		int height = map.getHeight();
		int width = map.getWidth();
		int windowHeight = this.getHeight();
		int windowWidth = this.getWidth();
		int i, x, y, xstart, ystart;
		int yHeight = windowHeight/height;
		int xWidth = windowWidth/width;

		ArrayList <Integer> widthList = new ArrayList <Integer> ();
		ArrayList <Integer> heightList = new ArrayList <Integer> ();
		
		int extraWidth = windowWidth - (xWidth * width);
		int extraHeight = windowHeight - (yHeight * height);
		
		if(extraWidth < 0)
			extraWidth = width - 1;
		
		if(extraHeight < 0)
			extraHeight = height - 1;
		
		//System.out.println("width = " + width + " windowWidth = " + windowWidth + " xWidth = " + xWidth + " extraWidth = " + extraWidth);
		
		widthList.add(0);
		heightList.add(0);
		
		int num1 = 0;
		
		for(i = 0; i <= width; i++) {
			if(extraWidth > 0)
				num1 = num1 + 1;
			widthList.add(num1);
			
			if(extraWidth > 0)
				extraWidth--;
		}
		
		num1 = 0;
		
		for(i = 0; i < height; i++) {
			if(extraHeight > 0)
				num1 = num1 + 1;
			heightList.add(num1);
			
			if(extraHeight > 0)
				extraHeight--;
		}
		
		/*
		for(i = 0; i < widthList.size(); i++) {
			System.out.print(widthList.get(i) + " ");
		}
		System.out.println();
		
		num1 = 0;
		for(i = 0; i <= width; i++) {
			num1 = (i * xWidth) + widthList.get(i);
			System.out.print(num1 + " ");
		}
		System.out.println();
		*/
		
		//gg.setColor(Color.BLACK);
		//gg.fillRect(0, 0, windowWidth, windowHeight);
		
        for(y = 0; y < height; y++) {
        	for(x = 0; x < width; x++) {
        		if(map.getCell(y, x).getState())
        			gg.setColor(Color.WHITE);
        		else
        			gg.setColor(Color.BLACK);
        		
        		gg.fillRect( (x * xWidth) + widthList.get(x), (y * yHeight) + heightList.get(y), xWidth + widthList.get(x + 1), yHeight + heightList.get(y + 1));
        	}
        }
        
        //This is where the vertical lines are printed.
        gg.setColor(DARKGREY);
        for(i = 0; i <= width; i++) {
            xstart = (i * xWidth) + widthList.get(i);
            gg.drawLine(xstart, 0, xstart, windowHeight);	//x1, y1, x2, y2
        }
        
        //This is where the horizontal lines are printed.
        gg.setColor(DARKGREY);
        for(i = 0; i <= height; i++) {
            ystart = (i * yHeight) + heightList.get(i);
            gg.drawLine(0, ystart, windowWidth, ystart);		
        }

        /*
        for(y = 0; y < height; y++) {
        	for(x = 0; x < width; x++) {
        		if(map.getCell(y, x).getState())
        	        gg.setColor(Color.BLACK);
        		else
        			gg.setColor(Color.WHITE);
        		gg.drawString(map.getCell(y, x).getCounter() + "", x * xWidth + xWidth / 2, y * yHeight + yHeight / 2);
        	}
        }
        */
	}
}
