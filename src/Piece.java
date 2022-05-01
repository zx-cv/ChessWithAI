package src;

public abstract class Piece{
  protected boolean isWhite;
  protected int rank,file;
  protected int r, c;

  public abstract boolean isLegal(int toR, int toC);
}