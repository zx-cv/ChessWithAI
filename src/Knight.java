package src;

import java.util.*;

public class Knight extends Piece{
  static int x=270, w = 90, h=90;
  private int y = 0;

  public Knight(boolean isWhite, int rank, int file){
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
    
    //up left
    if (rank > 1  && file > 0) {
      int r = rank -2;
      int f = file - 1;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }
    if (rank > 0 && file > 1) {
      int r = rank - 1;
      int f = file - 2;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }

    //up right
    if (rank > 1  && file < 7) {
      int r = rank -2;
      int f = file + 1;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }
    if (rank > 0 && file < 6) {
      int r = rank - 1;
      int f = file + 2;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }

    //down left
    if (rank < 6  && file > 0) {
      int r = rank + 2;
      int f = file - 1;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }
    if (rank < 7 && file > 1) {
      int r = rank + 1;
      int f = file - 2;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }

    //down right
    if (rank < 6  && file < 7) {
      int r = rank + 2;
      int f = file + 1;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }
    if (rank < 7 && file < 6) {
      int r = rank + 1;
      int f = file + 2;
      if (!board[r][f].hasPiece() || board[r][f].getPiece().isWhite() != this.isWhite) {
        ans.add(board[r][f]);
      }
    }

    return ans;
  }
}