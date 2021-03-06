package src;

import java.util.*;

public class Rook extends Piece{
    private static int x=360, w = 90, h=90;
    private int y = 0;
    private int moves = 0;
    public Rook(boolean isWhite, int rank, int file){
        this.isWhite = isWhite;
        if (!this.isWhite) y = 90;
        this.rank = rank;
        this.file = file;
        this.setImage(openImageFromSpriteSheet(x, y, w, h));
        this.value = 5;
    }

    public static int getX() {
        return x;
    }

    public String type() {
        return "Rook";
    }
    // doesn't account for checks
    public ArrayList<Square> getLegalMoves(Board b) {
        Square[][] board = b.getGrid();
        ArrayList<Square> ans = new ArrayList<>();

        // up
        int r = rank;
        int f = file;
        while (r > 0) {
            r--;
            // check if piece is in the way, then checks color
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                }
                break;
            }
            ans.add(board[r][f]);
        }

        // right
        r = rank;
        f = file;
        while (f < 7) {
            f++;
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                }
                break;
            }
            ans.add(board[r][f]);
        }

        // down
        r = rank;
        f = file;
        while (r < 7) {
            r++;
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                }
                break;
            }
            ans.add(board[r][f]);
        }

        // left
        r = rank;
        f = file;
        while (f > 0) {
            f--;
            // check if piece is in the way, then checks color
            if (board[r][f].hasPiece()) {
                if (board[r][f].getPiece().isWhite() != this.isWhite) {
                    ans.add(board[r][f]);
                }
                break;
            }
            ans.add(board[r][f]);
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