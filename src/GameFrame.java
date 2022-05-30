package src;

import java.awt.Color;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;
import java.io.*;

public class GameFrame extends JFrame {
	private int prints = 0;
	private static Game game = new Game();
	private static Board board = new Board(); // i chose a random color but maybe we could make it user input?
	private static double startTime;
	private static double blackTimeLeft = 600.0, whiteTimeLeft = 600.0;
	private static JLabel wtime, btime;
	//private static long wCurr = 10, bCurr = 600;
	private static JLabel pawnPromotion;
	private static JLabel start = new JLabel();
	private static JMenuBar mb;

	// starting dimensions of window (pixels)
	//public static final int WIDTH = 8*Square.getSide(), HEIGHT = 8*Square.getSide(), REFRESH = 40;
	private static final int WIDTH = 12*Square.getSide(), HEIGHT = (int)(9.5*Square.getSide()), REFRESH = 40;

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

	public static JLabel getPawnPromotion() {
		return pawnPromotion;
	}
	public static Board getBoard() {
		return board;
	}
	public static JLabel getWTime() {
		return wtime;
	}
	public static JLabel getBTime() {
		return btime;
	}
	public static Game getGame() {
		return game;
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

		//in game graphics
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
		
		//starting panel
		start.setBounds(0, 0, this.WIDTH, this.HEIGHT);
		panel.add(start);
		
		start.setForeground(new Color(101, 67, 33)); 
		JButton beginButton = new JButton("START");
		beginButton.setBounds(5*Square.getSide(), (int)(3.25*Square.getSide()), 2*Square.getSide(), Square.getSide());
		beginButton.setForeground(new Color(31, 14, 10)); 
		beginButton.setFont(new Font("Courier", Font.PLAIN, 20));
		start.add(beginButton);

		JLabel title = new JLabel("CHESS", SwingConstants.CENTER);
		title.setBackground(new Color(210, 180, 140)); 
		title.setForeground(new Color(31, 14, 10)); 
		title.setOpaque(true);
		title.setBounds(4*Square.getSide(), 2*Square.getSide(), 4*Square.getSide(), Square.getSide());
		title.setFont(new Font("Courier", Font.PLAIN, 50));
		start.add(title);

		JLabel chooseColor = new JLabel("CHOOSE A SIDE:", SwingConstants.CENTER);
		chooseColor.setBackground(new Color(210, 180, 140)); 
		chooseColor.setForeground(new Color(101, 67, 33)); 
		chooseColor.setOpaque(true);
		chooseColor.setBounds((int)(2.5*Square.getSide()), (int)(4.5*Square.getSide()), 3*Square.getSide(), Square.getSide());
		chooseColor.setFont(new Font("Courier", Font.PLAIN, 17));
		start.add(chooseColor);

		JButton blackButton = new JButton("BLACK");
		blackButton.setBounds(6*Square.getSide(), (int)(4.5*Square.getSide()), 2*Square.getSide(), Square.getSide());
		blackButton.setForeground(new Color(101, 67, 33)); 
		blackButton.setBackground(new Color(210, 180, 140));
		//blackButton.setBorderPainted(false); 
		blackButton.setOpaque(true);
		blackButton.setFont(new Font("Courier", Font.PLAIN, 20));
		start.add(blackButton);
		
		JButton whiteButton = new JButton("WHITE");
		whiteButton.setBounds((int)(8.25*Square.getSide()), (int)(4.5*Square.getSide()), 2*Square.getSide(), Square.getSide());
		whiteButton.setForeground(new Color(101, 67, 33)); 
		whiteButton.setBackground(new Color(210, 180, 140));
		//whiteButton.setBorderPainted(false);
		whiteButton.setOpaque(true);
		whiteButton.setFont(new Font("Courier", Font.PLAIN, 20));
		start.add(whiteButton);

		//menu
		mb = new JMenuBar();
		JMenu inGameMenu = new JMenu("MENU");
		JMenuItem quit = new JMenuItem("QUIT");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				quit();
			}
		});
		JMenuItem restart = new JMenuItem("RESTART");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restart();
			}
		});
		JMenu togglePossibleMoves = new JMenu("POSSIBLE MOVES");
		JMenuItem showMoves = new JMenuItem("SHOW");
		JMenuItem hideMoves = new JMenuItem("HIDE");
		hideMoves.setBackground(Color.YELLOW);
		showMoves.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.setShowMoves(true);
				showMoves.setBackground(Color.YELLOW);
				hideMoves.setBackground(Color.WHITE);
			}
		});
		hideMoves.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.setShowMoves(false);
				showMoves.setBackground(Color.WHITE);
				hideMoves.setBackground(Color.YELLOW);
			}
		});
		togglePossibleMoves.add(showMoves); togglePossibleMoves.add(hideMoves);
		inGameMenu.add(quit); inGameMenu.add(restart); inGameMenu.add(togglePossibleMoves);
		mb.add(inGameMenu);
		setJMenuBar(mb);
		mb.setVisible(false);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				clickedAt(me);
			}
		});
		
		timer = new Timer(REFRESH, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (game.isGameStarted()) {
					start.setVisible(false);
					wtime.setVisible(true);
					btime.setVisible(true);
					black.setVisible(true);
					white.setVisible(true);
					game.updateGame();
					panel.repaint();
					wtime.setText(getTime());
					btime.setText(getTime());
					mb.setVisible(true);
					if (getTime().equals("00:00") || game.isGameOver()) {
						gameOver();
						return;
					}
				} else {
					if (beginButton.getModel().isPressed()) {
						game.setGameStarted(true);
						board.generatePieces(board.isWhite());
						startTime = 1.0 * System.currentTimeMillis();
						return;
					}
					if (blackButton.getModel().isPressed()) {
						//System.out.println("black button is pressed");
						whiteButton.setBackground(new Color(210, 180, 140));
						blackButton.setBackground(Color.YELLOW);
						board.setWhite(false);
						panel.repaint();
						return;
					} else if (whiteButton.getModel().isPressed()) {
						//System.out.println("white button is pressed");
						blackButton.setBackground(new Color(210, 180, 140));
						whiteButton.setBackground(Color.YELLOW);
						board.setWhite(true);
						panel.repaint();
						return;
					}
					
					start.setVisible(true);
					start.setBackground(new Color(210, 180, 140));
        			start.setOpaque(true);
					wtime.setVisible(false);
					btime.setVisible(false);
					black.setVisible(false);
					white.setVisible(false);
					panel.repaint();
				}
				
			}
		});
		
		timer.start();
		this.setVisible(true);
		panel.requestFocusInWindow();

	}

	protected void clickedAt(MouseEvent me) {
		//System.out.println("You just clicked "+me);	
		if (game.isGameStarted())
			try {
				board.justClicked(me);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			t = 1200 - ((System.currentTimeMillis() - startTime) / 1000 - 1) - blackTimeLeft;
			//t = 615 - ((System.currentTimeMillis() - startTime) / 1000 - 1) - blackTimeLeft;
			whiteTimeLeft = t;
		} else {
			t = 1200 - ((System.currentTimeMillis() - startTime) / 1000 - 2) - whiteTimeLeft;
			//t = 615 - ((System.currentTimeMillis() - startTime) / 1000 - 2) - whiteTimeLeft;
			blackTimeLeft = t;
		}
		if (t == 0) game.setGameOver(true);
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
		Object[] options = {"EXIT", "RESTART"};
		// JOptionPane optionPane = ;
		// JDialog dialog = new JDialog(this, "Click a button", true);
		// dialog.setContentPane(optionPane);
		// dialog.setDefaultCloseOperation(
		// JDialog.DO_NOTHING_ON_CLOSE);
		// dialog.addWindowListener(new WindowAdapter() {
		// public void windowClosing(WindowEvent we) {
		// 	setLabel("Thwarted user attempt to close window.");
		// }});
		// optionPane.addPropertyChangeListener( new PropertyChangeListener() {
		// 	public void propertyChange(PropertyChangeEvent e) {
		// 	String prop = e.getPropertyName();

		// 	if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {

		// 	dialog.setVisible(false);
		// }
		// }});
		// dialog.pack();
		// dialog.setVisible(true);
		int n = JOptionPane.showOptionDialog(this, "Would you like to exit or begin a new game?", "GAME OVER", JOptionPane.YES_NO_OPTION,
    		JOptionPane.QUESTION_MESSAGE, null, options, options[0]); //default button title
		if (n == 0) { //exit
			quit();
		} else {
			restart();
		}
	}

	public void quit() {
		this.setVisible(false);
		System.exit(0);
	}
	public void restart() {
		// game.setGameOver(false);
		// game.setGameStarted(false);
		board = new Board();
		game = new Game();
		start.setVisible(true);
		start.setBackground(new Color(210, 180, 140));
    	start.setOpaque(true);
		mb.setVisible(false);
	}
}
