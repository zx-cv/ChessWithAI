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

public class PawnMenu extends JFrame{
    private GameFrame gf;

	// starting dimensions of window (pixels)
	public static final int WIDTH = Square.getSide(), HEIGHT = 4*Square.getSide(), REFRESH = 40;

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

    public PawnMenu(String s) {
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
		panel.requestFocusInWindow();

	}

	protected void clickedAt(MouseEvent me) {
		//System.out.println("You just clicked "+me);	
		gf.board.justClicked(me);
		panel.repaint();
	}
}
