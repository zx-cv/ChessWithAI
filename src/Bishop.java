package src;
import java.util.*;

public class Bishop extends Piece{
  static int x=180, w = 90, h=90;
  private int y = 0;
  
  public Bishop(boolean isWhite, int rank, int file){
    this.isWhite = isWhite;
    if (!this.isWhite) y = 90;
    this.rank = rank;
    this.file = file;
    this.setImage(openImageFromSpriteSheet(x, y, w, h));
    this.value = 3;
  }

  
  //doesn't account for checks
  public ArrayList<Square> getLegalMoves(Board b){
    Square[][] board = b.getGrid();
    ArrayList<Square> ans = new ArrayList<>();
    Piece p = board[rank][file].getPiece();
    
    //top left
    int r = rank;
    int f = file;
    while(r > 0 && f > 0){
      r--;
      f--;
      //check if piece is in the way, then checks color
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != this.isWhite) {
          ans.add(board[r][f]);
        }
        break;
      } 
      ans.add(board[r][f]);
    }
    
    //top right
    r = rank;
    f = file;
    while(r > 0 && f < 7){
      r--;
      f++;
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != this.isWhite) {
          ans.add(board[r][f]);
        }
        break;
      } 
      ans.add(board[r][f]);
    }

    //bottom left
    r = rank;
    f = file;
    while(r < 7 && f > 0){
      r++;
      f--;
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != this.isWhite) {
          ans.add(board[r][f]);
        }
        break;
      }
      ans.add(board[r][f]);
    }

    //bottom right
    r = rank;
    f = file;
    while(r < 7 && f < 7){
      r++;
      f++;
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != this.isWhite) {
          ans.add(board[r][f]);
        }
        break;
      }
      ans.add(board[r][f]);
    }
    return ans;
  }
}