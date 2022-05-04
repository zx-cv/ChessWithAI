package src;

import java.util.*;

public class Pawn extends Piece {
    public Pawn(boolean isWhite, int rank, int file) {
        this.isWhite = isWhite;
        this.rank = rank;
        this.file = file;
    }

    //Doesn't account for checks
    public ArrayList<Square> getLegalMoves(Board b) {
        int dir = (isWhite ^ b.isWhite()) ? -1 : 1;
        Square[][] board = b.getGrid();
        ArrayList<Square> ans = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            try {
                if (board[rank-1][file])
            }
        }
    }
}