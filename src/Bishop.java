package src;
import java.util.*;

public class Bishop extends Piece{
  
  public Bishop(boolean isWhite, int rank, int file){
    this.isWhite = isWhite;
    this.rank = rank;
    this.file = file;
  }

  
  //doesn't account for checks
  public List<Square> getLegalMoves(Board board){
    List<Square> ans = new ArrayList<>();
    Piece p = board[r][f].getPiece();
    
    //top left
    r = rank;
    f = file;
    while(r >= 0 && f >= 0){
      r--;
      f--;
      //check if piece is in the way, then checks color
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != isWhite{
          ans.add(board[r][f]);
          break;
        }
      }
      ans.add(board[r][f]);
    }
    
    //top right
    r = rank;
    f = file;
    while(r >= 0 && f < 8){
      r--;
      f++;
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != isWhite{
          ans.add(board[r][f]);
          break;
        }
      }
      ans.add(board[r][f]);
    }

    //bottom left
    r = rank;
    f = file;
    while(r < 8 && f >= 0){
      r++;
      f--;
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != isWhite{
          ans.add(board[r][f]);
          break;
        }
      }
      ans.add(board[r][f]);
    }

    //bottom right
    r = rank;
    f = file;
    while(r < 8 && f < 8){
      r++;
      f++;
      if(board[r][f].hasPiece()){
        if(board[r][f].getPiece().isWhite() != isWhite{
          ans.add(board[r][f]);
          break;
        }
      }
      ans.add(board[r][f]);
    }

    return ans;
  }
  
}