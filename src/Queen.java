package src;
import java.util.*;

public class Queen extends Piece{
    public Queen(boolean isWhite, int rank, int file){
        this.isWhite = isWhite;
        this.rank = rank;
        this.file = file;
    }

    public ArrayList<Square> getLegalMoves(Board board) {
        return null;
    }
}