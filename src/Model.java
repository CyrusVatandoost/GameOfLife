import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Model {
	
	private Map map;
	private JFrame frame;
	private int mouseX, mouseY;
	
	public Model() {
		//Reader reader = new Reader("test2.txt");
		//map = reader.getMap();
		map = new Map(9, 16);
		map.createMap(20);
		//setDisplay();
		setController();
		runModel();
	}
	
	public void runModel() {
		map.updateMap();
	}
	
	public void setController() {
		Controller controller = new Controller(map);
	}
	
	public void setDisplay() {
		frame = new JFrame();
		frame.setSize(1000, 500);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(new Display(map));
		frame.setVisible(true);
		frame.getContentPane().addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {

		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
		        mouseX = e.getX();
		        mouseY = e.getY();
		        
				int xWidth = (int) frame.getWidth() / map.getWidth();
				int yHeight = (int) frame.getHeight() / map.getHeight();
		        
				mouseX = mouseX / xWidth;
				mouseY = mouseY / yHeight;
			
				map.getCell(mouseY, mouseX).setState();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_SPACE)
					map.togglePlayPause();
				if(arg0.getKeyCode() == KeyEvent.VK_C)
					map.createMap();
				if(arg0.getKeyCode() == KeyEvent.VK_N)
					map.nextStep();
				if(arg0.getKeyCode() == KeyEvent.VK_R)
					map.randomMap();
			}
		});
	}
	
	public Map getMap() {
		return map;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
