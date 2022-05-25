package src;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;

public class Game {
	
	private static boolean gameOver = false;
	private static boolean gameStarted = false;
    public Game(){
        
    }

	public static boolean isGameOver() {
		return gameOver;
	}
	public static void setGameOver(boolean b) {
		gameOver = b;
	}
	public static boolean isGameStarted() {
		return gameStarted;
	}
	public static void setGameStarted(boolean b) {
		gameStarted = b;
	}

	/**
	 * This is called every time the Timer goes off.  Right now, it moves all 
	 * the Objects and checks for collisions.  This is common in games with flying
	 * Objects.  You can do more, though.  Like add items or move to new screens
	 * or check to see if the turn is over or...
	 */
	public void updateGame() {
		if (gameOver) return;
		// System.out.println("Moving Objects!!");
		moveObjects();
		checkGameOver();
		
	}
    /**
	 * Right now I am checking for collisions between GameObjects
	 */
	public void checkGameOver() {
		if (gameOver) {
			System.exit(0);
		}
		
	}

	/**
	 * get it...
	 */
	public void moveObjects() {
		
	}


	/**
	 * Draws all the stuff in the game without changing them
	 * No reason to change this unless you wanted a background
	 * or something.
	 * @param g
	 */
    public void drawTheGame(Graphics g) {
		GameFrame.getBoard().draw(g);
		for (Piece p: GameFrame.getBoard().getWhitePieces()) {
			p.draw(g);
		}
		for (Piece p: GameFrame.getBoard().getBlackPieces()) {
			p.draw(g);
		}
		
	}

    

}
