package src;

import java.util.*;
import java.awt.*;

public class Pawn extends Piece{
    static int x=450, w = 90, h=90;
    private int y = 0;
    private boolean firstMove = true;
    private boolean secondMove = true;
    private boolean isPawn = true;

    public Pawn(boolean isWhite, int rank, int file){
        this.isWhite = isWhite;
        if (!this.isWhite) y = 90;
        this.rank = rank;
        this.file = file;
        this.setImage(openImageFromSpriteSheet(x, y, w, h));
    }
    
    @Override
    public boolean isPawn() {
        return true;
    }
    //Doesn't account for checks
    public ArrayList<Square> getLegalMoves(Board b) {
        int dir = (isWhite ^ b.isWhite()) ? 1 : -1;
        Square[][] board = b.getGrid();
        ArrayList<Square> ans = new ArrayList<>();
        if (firstMove) {
            if (!board[rank+dir][file].hasPiece() && !board[rank+(2*dir)][file].hasPiece()) {
                ans.add(board[rank+(2*dir)][file]);
            }
        }
        if (!board[rank+dir][file].hasPiece()) {
            ans.add(board[rank+dir][file]);
        }
        for (int f = file - 1; f < file + 2; f += 2) {
            if (f < 0 || f > 7) {
                continue;
            }
            if (board[rank+dir][f].hasPiece()) {
                ans.add(board[rank+dir][f]);
            }
            if (board[rank][f].hasPiece() && board[rank][f].getPiece().isPawn() && ((Pawn) board[rank][f].getPiece()).secondMove && (board[rank][f].getPiece().isWhite() != this.isWhite)) {
                ans.add(board[rank+dir][f]);
            }
        }
        return ans;
    }

    public ArrayList<Square> getAttackMoves(Board b) {
        ArrayList<Square> ans = getLegalMoves(b);


        for (int i = 0; i < ans.size(); i++) {
            if (ans.get(i).getFile() == file) {
                ans.remove(i);
            }
        }

        return ans;
    }
    @Override
    public void moveTo(int r, int f) {
        super.moveTo(r, f);
        if (firstMove && secondMove) {
            firstMove = true;
            secondMove = false;
        } else if (firstMove) {
            firstMove = false;
            secondMove = true;
        } else if (secondMove) {
            secondMove = false;
        }
    }
    
}