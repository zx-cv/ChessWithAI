package src;

import java.util.*;

public class Knight extends Piece {
  private static int x = 270, w = 90, h = 90;
  private int y = 0;

  public Knight(boolean isWhite, int rank, int file) {
    this.isWhite = isWhite;
    if (!this.isWhite)
      y = 90;
    this.rank = rank;
    this.file = file;
    this.setImage(openImageFromSpriteSheet(x, y, w, h));
    this.value = 2; //it's technically 3, but this is for sorting purposes
  }

  public static int getX() {
    return x;
  }

  // doesn't account for checks
  public ArrayList<Square> getLegalMoves(Board b) {
    Square[][] board = b.getGrid();
    ArrayList<Square> ans = new ArrayList<>();
    for (int i = -1; i < 2; i += 2) {
      for (int j = -1; j < 2; j += 2) {
        try {
          Square s = board[rank + (j) * 2][file + i];
          if (!s.hasPiece() || s.getPiece().isWhite() != isWhite) {
            ans.add(s);
          } else {
          }
        } catch (Exception e) {
        }
        try {
          Square s = (board[rank + (j)][file + i * 2]);
          if (!s.hasPiece() || s.getPiece().isWhite() != isWhite) {
            ans.add(s);
          } else {
          }
        } catch (Exception e) {
        }
      }
    }
    return ans;
  }
}