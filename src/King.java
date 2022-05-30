package src;
import java.util.*;

public class King extends Piece{
  private static int x=0, w = 90, h=90;
  private int y = 0;
  private int moves = 0; //if moves is > 1 then it has moved
  private static boolean whiteKingCheck;
  private static boolean blackKingCheck;

  public King(boolean isWhite, int rank, int file){
    this.isWhite = isWhite;
    if (!isWhite) y = 90;
    this.rank = rank;
    this.file = file;
    this.setImage(openImageFromSpriteSheet(x, y, w, h));
  }

  public String type() {
    return "King";
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
        if(s.hasPiece() && s.getPiece().isWhite() == isWhite){
          continue;
        }
        ans.add(s);
      }
    }
    return ans;
  }

  public void moveTo(int r, int f) {
    moves++;
    this.rank = r;
    this.file = f;
  }

  public boolean moved() {
    return moves > 1;
  }

  public void subtractMove() {
    moves--;
  }
}