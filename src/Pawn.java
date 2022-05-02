package src;
import java.util.*;

public class Pawn extends Piece{
    public Pawn(boolean isWhite, int rank, int file){
        this.isWhite = isWhite;
        this.rank = rank;
        this.file = file;
    }

    public ArrayList<Square> getLegalMoves(Board b) {

    }
}