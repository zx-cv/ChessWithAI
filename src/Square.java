package src;
import java.awt.*;

public class Square{
  
  private boolean isWhite;
  private Piece piece;
  private static int side = 50;
  private int rank, file;

  public Square(boolean isWhite, int r, int c){
    this.rank = r;
    this.file = c;
    this.isWhite = isWhite;
  }

  public boolean hasPiece(){
    return piece != null;
  }

  public Piece getPiece(){
    return piece;
  }

  public int getRank() {
    return rank;
  }

  public int getFile() {
    return file;
  }

  public boolean getIsWhite() {
    return isWhite;
  }

  public void placePiece(Piece p){
    p.moveTo(rank, file);
    piece = p;
  }

  public void capture() {
    if (piece.isWhite()) {
      for (int i = 0; i < GameFrame.getBoard().getWhitePieces().size(); i++) {
        if (GameFrame.getBoard().getWhitePieces().get(i) == piece) {
          GameFrame.getBoard().getWhitePieces().remove(i);
        }
      }
    } else {
      for (int i = 0; i < GameFrame.getBoard().getBlackPieces().size(); i++) {
        if (GameFrame.getBoard().getBlackPieces().get(i) == piece) {
          GameFrame.getBoard().getBlackPieces().remove(i);
        }
      }
    }
    piece = null;
  }

  public void removePiece() {
    piece = null;
  }

  public void draw(Graphics g) {
    if (isWhite) g.setColor(new Color(210, 180, 140));
    else g.setColor(new Color(101, 67, 33));
    g.fillRect(file*side+2*side, rank*side+side/2, side, side);
    
  }

  public static int getSide() {
    return side;
  }
}