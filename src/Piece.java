package src;
import java.util.*;

public abstract class Piece{
  protected boolean isWhite;
  protected int rank,file;
  protected int r, c;

  //returns a list of legal squares it can move to
  public abstract ArrayList<Square> getLegalMoves(Board board);

  public boolean isWhite() {
    return isWhite;
  }
}