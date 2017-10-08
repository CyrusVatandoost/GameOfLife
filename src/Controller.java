import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Controller extends JFrame {
	
	private int mouseX, mouseY;
	private JPanel contentPane;
	
	public Controller(Map map) {
		setSize(1000, 500);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		
		add(new Display(map));
		getContentPane().addMouseListener(new MouseListener() {
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
		        
				int xWidth = (int) getWidth() / map.getWidth();
				int yHeight = (int) getHeight() / map.getHeight();
		        
				mouseX = mouseX / xWidth;
				mouseY = mouseY / yHeight;
			
				map.getCell(mouseY, mouseX).setState();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addKeyListener(new KeyAdapter() {
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

		setVisible(true);
	}
}
