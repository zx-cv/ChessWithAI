package src;

import java.util.*;

public class Knight extends Piece {
  public Knight(boolean isWhite, int rank, int file) {
    this.isWhite = isWhite;
    this.rank = rank;
    this.file = file;
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