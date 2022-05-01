package src;

public abstract class Piece{
  protected boolean isWhite;
  protected int rank,file;
  protected int r, c;

  public abstract boolean isLegal(int toR, int toC);

  //returns a list of legal squares it can move to
  public abstract List<Piece> getLegalMoves(Board board);
}