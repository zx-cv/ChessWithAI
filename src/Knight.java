package src;

public class Knight extends Piece{
  public Knight(boolean isWhite, int rank, int file, int r, int c){
    this.isWhite = isWhite;
    this.rank = rank;
    this.file = file;
    this.r = r;
    this.c = c;
  }

  public boolean isLegal(int toR, int toC) {
    if (!Board.inBounds(toR, toC)) return false;
    int rDiff = Math.abs(toR - this.r);
    int cDiff = Math.abs(toC - this.c);
    return (rDiff == 2 && cDiff == 1 || rDiff == 1 && cDiff == 2);
  }
}