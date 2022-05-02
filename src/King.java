package src;
import java.util.*;

public class King extends Piece{
  public King(boolean isWhite, int rank, int file){
    this.isWhite = isWhite;
    this.rank = rank;
    this.file = file;
  }

  //doesnt account for checks yet
  public ArrayList<Square> getLegalMoves(Board b){
    Square[][] board = b.getGrid();
    ArrayList<Square> ans = new ArrayList<>();
    for(int i=-1;i<2;i++){
      for(int j=-1;j<2;j++){
        if((i==0 && j == 0) || rank+i < 0 || rank+i > 7 || file+j < 0 || file+j > 7){
          continue;
        }
        Square s = board[rank+i][file+j];
        if(s.getPiece().isWhite() == isWhite){
          continue;
        }
        ans.add(s);
      }
    }
    return ans;
  }

  
}