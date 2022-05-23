package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import javax.swing.*;


public class StartMenu extends JFrame{
    private GameFrame gf;

	// starting dimensions of window (pixels)
	public static final int WIDTH = 12*Square.getSide(), HEIGHT = 9*Square.getSide(), REFRESH = 40;

	JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;

	// where the game objects are displayed
	private JPanel panel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// tried to get rid of some stuttering, changing REFRESH 
			// improved this issue
			panel.getToolkit().sync();
		}
	};
	private Timer timer;//timer that runs the game

    public StartMenu(String s) {
        super(s);
        setUpStuff();
    }
	/**
	 * Sets up the panel, timer, other initial objects in the game.
	 * The Timer goes off every REFRESH milliseconds.  Every time the
	 * Timer goes off, the game is told to update itself and then the 
	 * view is refreshed.  
	 */
	private void setUpStuff() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.add(panel);
		this.pack();
		panel.setLayout(null);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				clickedAt(me);
			}
		});
		
		timer = new Timer(REFRESH, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.repaint();
			}
		});
		
		timer.start();
		this.setVisible(true);
		menu.setVisible(true);
		panel.requestFocusInWindow();

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("File menu");
		menuBar.add(menu);
	
		//JMenuItems show the menu items
		menuItem = new JMenuItem("New");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menu.add(menuItem);
	
		// add a separator
		menu.addSeparator();
	
		menuItem = new JMenuItem("Pause");
		menuItem.setMnemonic(KeyEvent.VK_P);
		menu.add(menuItem);
	
		menuItem = new JMenuItem("Exit");
		menuItem.setMnemonic(KeyEvent.VK_E);
		menu.add(menuItem);
	
		// add menu bar to frame
		gf.setJMenuBar(menuBar);

	}

	protected void clickedAt(MouseEvent me) {
		//System.out.println("You just clicked "+me);	
		gf.board.justClicked(me);
		panel.repaint();
	}
}
