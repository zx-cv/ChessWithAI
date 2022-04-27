package src;
import java.awt.*;
import java.awt.event.*;
public class Board{
  private Square[][] board = new Square[8][8];
  
  public Board(){
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
    generatePieces();
  }

  private void generatePieces(){
    
  }

  public void justClicked(MouseEvent me) {

  }
}