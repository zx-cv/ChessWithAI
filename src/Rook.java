package src;

import java.util.*;

public class Rook extends Piece {
    public Rook(boolean isWhite, int rank, int file) {
        this.isWhite = isWhite;
        this.rank = rank;
        this.file = file;
    }

    // doesn't account for checks
    public ArrayList<Square> getLegalMoves(Board b) {
        Square[][] board = b.getGrid();
        ArrayList<Square> ans = new ArrayList<>();

        // up
        int r = rank;
        int f = file;
        while (r >= 0) {
            r--;
            // check if piece is in the way, then checks color
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                    break;
                }
            }
            ans.add(board[r][f]);
        }

        // right
        r = rank;
        f = file;
        while (f < 8) {
            f++;
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                    break;
                }
            }
            ans.add(board[r][f]);
        }

        // down
        r = rank;
        f = file;
        while (r < 8) {
            r++;
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                    break;
                }
            }
            ans.add(board[r][f]);
        }

        // left
        r = rank;
        f = file;
        while (f >= 0) {
            f--;
            // check if piece is in the way, then checks color
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                    break;
                }
            }
            ans.add(board[r][f]);
        }

        return ans;
    }
}