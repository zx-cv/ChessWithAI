package src;
import java.util.*;

public class Rook extends Piece{
    static int x=360, w = 90, h=90;
    private int y = 0;
    public Rook(boolean isWhite, int rank, int file){
        this.isWhite = isWhite;
        if (!this.isWhite) y = 90;
        this.rank = rank;
        this.file = file;
        this.setImage(openImageFromSpriteSheet(x, y, w, h));
    }

    public ArrayList<Square> getLegalMoves(Board board) {

    }
}