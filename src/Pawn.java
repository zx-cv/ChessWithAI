package src;

import java.util.*;
import java.awt.*;

public class Pawn extends Piece{
    static int x=450, w = 90, h=90;
    private int y = 0;
    public Pawn(boolean isWhite, int rank, int file){
        this.isWhite = isWhite;
        if (!this.isWhite) y = 90;
        this.rank = rank;
        this.file = file;
        this.setImage(openImageFromSpriteSheet(x, y, w, h));
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