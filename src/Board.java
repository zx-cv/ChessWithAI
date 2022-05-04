package src;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Board{
  private Square[][] grid = new Square[8][8];
  private ArrayList<Piece> whitePieces = new ArrayList<>();
  private ArrayList<Piece> blackPieces = new ArrayList<>();
  private boolean isWhite;
  
  public Board(boolean isWhite){
    this.isWhite = isWhite;
    for(int i = 0; i < 8; i++){
      for(int j = 0; j <8; j++){
        if((i%2 + j%2)%2 == 0){
          grid[i][j] = new Square(true, i * Square.getSide(), j * Square.getSide());
        }
        else{
          grid[i][j] = new Square(false, i * Square.getSide(), j * Square.getSide());
        }
      }
    }
    generatePieces(isWhite);
  }

  public Square[][] getGrid() {
    return grid;
  }

  //initializes and place pieces on the board
  //isWhite == true will make white on bottom and vice versa
  private void generatePieces(boolean isWhite){
    //Pawns
    for(int i=0;i<8;i++){
      grid[1][i].placePiece(new Pawn(!isWhite,1,i));
      grid[6][i].placePiece(new Pawn(isWhite,6,i));
    }

    //Rooks
    grid[0][0].placePiece(new Rook(!isWhite,0,0));
    grid[0][7].placePiece(new Rook(!isWhite,0,7));
    grid[7][0].placePiece(new Rook(isWhite,7,0));
    grid[7][7].placePiece(new Rook(isWhite,7,7));

    //Knights
    grid[0][1].placePiece(new Knight(!isWhite,0,1));
    grid[0][6].placePiece(new Knight(!isWhite,0,6));
    grid[7][1].placePiece(new Knight(isWhite,7,1));
    grid[7][6].placePiece(new Knight(isWhite,7,6));

    //Bishops
    grid[0][2].placePiece(new Bishop(!isWhite,0,2));
    grid[0][5].placePiece(new Bishop(!isWhite,0,5));
    grid[7][2].placePiece(new Bishop(isWhite,7,2));
    grid[7][5].placePiece(new Bishop(isWhite,7,5));

    //King and Queen
    if(isWhite){
      grid[0][3].placePiece(new Queen(!isWhite,0,3));
      grid[0][4].placePiece(new King(!isWhite,0,4));
      grid[7][3].placePiece(new Queen(isWhite,7,3));
      grid[7][4].placePiece(new King(isWhite,7,4));
    }
    else{
      grid[0][3].placePiece(new King(!isWhite,0,3));
      grid[0][4].placePiece(new Queen(!isWhite,0,4));
      grid[7][3].placePiece(new King(isWhite,7,3));
      grid[7][4].placePiece(new Queen(isWhite,7,4));
    }

    //adding each piece to blackPieces/whitePieces
    if(isWhite){
      for(int i=0;i<8;i++){
        blackPieces.add(grid[0][i].getPiece());
        blackPieces.add(grid[1][i].getPiece());
        whitePieces.add(grid[6][i].getPiece());
        whitePieces.add(grid[7][i].getPiece());
      }
    }
    else{
      for(int i=0;i<8;i++){
        whitePieces.add(grid[0][i].getPiece());
        whitePieces.add(grid[1][i].getPiece());
        blackPieces.add(grid[6][i].getPiece());
        blackPieces.add(grid[7][i].getPiece());
      }
    }
  }

  public boolean isWhite() {
    return isWhite;
  }

  public void justClicked(MouseEvent me) {
    
  }

  public boolean inBounds(int toR, int toC) {
    return toR >= 0 && toR < grid.length && toC >= 0 && toC < grid[0].length;
  }

  public void draw(Graphics g) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        grid[i][j].draw(g);
      }
    }
  }
}