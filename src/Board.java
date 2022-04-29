package src;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Board{
  private Square[][] board = new Square[8][8];
  private List<Piece> whitePieces = new ArrayList<>();
  private List<Piece> blackPieces = new ArrayList<>();
  
  public Board(boolean isWhite){
    for(int i = 0; i < 8; i++){
      for(int j = 0; j <8; j++){
        if((i%2 + j%2)%2 == 0){
          board[i][j] = new Square(true);
        }
        else{
          board[i][j] = new Square(false);
        }
      }
    }
    generatePieces(isWhite);
  }

  //initializes and place pieces on the board
  //isWhite == true will make white on bottom and vice versa
  private void generatePieces(boolean isWhite){
    //Pawns
    for(int i=0;i<8;i++){
      board[1][i].placePiece(new Pawn(!isWhite,1,i));
      board[6][i].placePiece(new Pawn(isWhite,6,i));
    }

    //Rooks
    board[0][0].placePiece(new Rook(!isWhite,0,0));
    board[0][7].placePiece(new Rook(!isWhite,0,7));
    board[7][0].placePiece(new Rook(isWhite,7,0));
    board[7][7].placePiece(new Rook(isWhite,7,7));

    //Knights
    board[0][1].placePiece(new Knight(!isWhite,0,1));
    board[0][6].placePiece(new Knight(!isWhite,0,6));
    board[7][1].placePiece(new Knight(isWhite,7,1));
    board[7][6].placePiece(new Knight(isWhite,7,6));

    //Bishops
    board[0][2].placePiece(new Bishop(!isWhite,0,2));
    board[0][5].placePiece(new Bishop(!isWhite,0,5));
    board[7][2].placePiece(new Bishop(isWhite,7,2));
    board[7][5].placePiece(new Bishop(isWhite,7,5));

    //King and Queen
    if(isWhite){
      board[0][3].placePiece(new Queen(!isWhite,0,3));
      board[0][4].placePiece(new King(!isWhite,0,4));
      board[7][3].placePiece(new Queen(isWhite,7,3));
      board[7][4].placePiece(new King(isWhite,7,4));
    }
    else{
      board[0][3].placePiece(new King(!isWhite,0,3));
      board[0][4].placePiece(new Queen(!isWhite,0,4));
      board[7][3].placePiece(new King(isWhite,7,3));
      board[7][4].placePiece(new Queen(isWhite,7,4));
    }

    //adding each piece to blackPieces/whitePieces
    if(isWhite){
      for(int i=0;i<8;i++){
        blackPieces.add(board[0][i].getPiece());
        blackPieces.add(board[1][i].getPiece());
        whitePieces.add(board[6][i].getPiece());
        whitePieces.add(board[7][i].getPiece());
      }
    }
    else{
      for(int i=0;i<8;i++){
        whitePieces.add(board[0][i].getPiece());
        whitePieces.add(board[1][i].getPiece());
        blackPieces.add(board[6][i].getPiece());
        blackPieces.add(board[7][i].getPiece());
      }
    }
  }

  public void justClicked(MouseEvent me) {

  }
}