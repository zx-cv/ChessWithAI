package src;

import java.util.*;

<<<<<<< HEAD
public class Knight extends Piece{
  static int x=270, w = 90, h=90;
  private int y = 0;

  public Knight(boolean isWhite, int rank, int file){
=======
public class Knight extends Piece {
  public Knight(boolean isWhite, int rank, int file) {
>>>>>>> fa3fa8136bb11d2bd215e9afa557df3d530e1810
    this.isWhite = isWhite;
    if (!this.isWhite) y = 90;
    this.rank = rank;
    this.file = file;
    this.setImage(openImageFromSpriteSheet(x, y, w, h));
  }

  // doesn't account for checks
  public ArrayList<Square> getLegalMoves(Board b) {
    Square[][] board = b.getGrid();
    ArrayList<Square> ans = new ArrayList<>();
    for (int i = -1; i < 2; i += 2) {
      for (int j = -1; j < 2; j += 2) {
        try {
          ans.add(board[rank + (j) * 3][file + i]);
        } catch (Exception e) {

        }
        try {
          ans.add(board[rank + (j)][file + i * 3]);
        } catch (Exception e) {
          
        }
      }
    }
    return ans;
  }
}