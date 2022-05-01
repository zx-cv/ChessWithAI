package src;

public class King extends Piece{
  public King(boolean isWhite, int rank, int file){
    this.isWhite = isWhite;
    this.rank = rank;
    this.file = file;
  }

  public boolean isLegal(int toR, int toC) {
    if (!Board.inBounds(toR, toC) || toR == this.r && toC == this.c) return false;
    int rDiff = Math.abs(toR - this.r);
    int cDiff = Math.abs(toC - this.c);
    return (rDiff <= 1 && cDiff <= 1);
  }
}