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

public class GameFrame extends JFrame {
	private int prints = 0;
	private Game game = new Game();
	public static Board board = new Board(true); // i chose a random color but maybe we could make it user input?
	public static double startTime = 1.0 * System.currentTimeMillis();
	public static double blackTimeLeft = 600.0, whiteTimeLeft = 600.0;
	public static JLabel wtime, btime;
	public static long wCurr = 10, bCurr = 600;
	public static JLabel pawnPromotion;

	// starting dimensions of window (pixels)
	//public static final int WIDTH = 8*Square.getSide(), HEIGHT = 8*Square.getSide(), REFRESH = 40;
	public static final int WIDTH = 12*Square.getSide(), HEIGHT = 9*Square.getSide(), REFRESH = 40;

	// where the game objects are displayed
	private JPanel panel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			game.drawTheGame(g);
			// tried to get rid of some stuttering, changing REFRESH 
			// improved this issue
			panel.getToolkit().sync();
		}
	};
	private Timer timer;//timer that runs the game


	public GameFrame(String string) {
		super(string);
		//pm = new PawnMenu(string);
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
		wtime = new JLabel(getTime());
		wtime.setBounds(524, 25, 50, 25);
		panel.add(wtime);
		btime = new JLabel(getTime());
		btime.setBounds(29, 25, 50, 25);
		panel.add(btime);
		JLabel black = new JLabel("Black");
		black.setBounds(30, 50, 50, 25);
		panel.add(black);
		JLabel white = new JLabel("White");
		white.setBounds(525, 50, 50, 25);
		panel.add(white);
		pawnPromotion = new JLabel();
		pawnPromotion.setLayout(new BoxLayout(pawnPromotion, BoxLayout.Y_AXIS));
		panel.add(pawnPromotion);


		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				clickedAt(me);
			}
		});
		
		timer = new Timer(REFRESH, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (getTime().equals("00:00") || Game.gameOver) {
					gameOver();
					return;
				}
				game.updateGame();
				panel.repaint();
				wtime.setText(getTime());
				btime.setText(getTime());
			}
		});
		
		timer.start();
		this.setVisible(true);
		panel.requestFocusInWindow();

	}

	protected void clickedAt(MouseEvent me) {
		//System.out.println("You just clicked "+me);	
		board.justClicked(me);
		panel.repaint();
	}

	// public static long getTime() {
		// String s = "";
		// double t = (System.currentTimeMillis() - startTime) / 1000 - 1;
		// if (t >= 3600) {
		// 	s += (int) t / 3600;
		// 	s += ":";
		// }
		// t %= 3600;
		// int i = (int)t / 60;
		// if (i < 10) s += "0";
		// s +=  i + ":";
		// i = (int)t % 60;
		// if (i < 10) s += "0";
		// s += i;
		// //System.out.println(s);
		// return s;
		
		// double t = 600 - ((System.currentTimeMillis() - startTime) / 1000 - 2);
		// long t;
		// long x = System.currentTimeMillis();
		// if (Board.moveWhite) t = wCurr - ((x - startTime)/1000 - 1);
		// else t = bCurr - ((x - startTime)/1000);
		// System.out.println("curr: " + x);
		// System.out.println("start: " + startTime);
		// System.out.println("x-start: " + (x-startTime));
		// System.out.println("seconds: " + (x-startTime) / 1000);
		// System.out.println("countdown: " + t);
		// return t;
	// }

	private String getTime() {
		String s = "";
		double t;
		if (board.whiteMove()) {
			t = 1200 - ((System.currentTimeMillis() - startTime) / 1000 - 2) - blackTimeLeft;
			whiteTimeLeft = t;
		} else {
			t = 1200 - ((System.currentTimeMillis() - startTime) / 1000 - 2) - whiteTimeLeft;
			blackTimeLeft = t;
		}
		if (t == 0) Game.gameOver = true;
		int i = (int)t / 60;
		if (i < 10) s += "0";
		s +=  i + ":";
		i = (int)t % 60;
		if (i < 10) s += "0";
		s += i;
		//System.out.println(s);
		return s;
	}

	public void gameOver() {
		this.setVisible(false);

	}

}
