package src;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;

public class Game {
	
	public static boolean gameOver = false;
    public Game(){
        
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
		//check if checkmate
		
		//check for draw
		
		//check for stalemate
		
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
		GameFrame.board.draw(g);
		for (Piece p: GameFrame.board.getWhitePieces()) {
			p.draw(g);
		}
		for (Piece p: GameFrame.board.getBlackPieces()) {
			p.draw(g);
		}

	}

    

}
