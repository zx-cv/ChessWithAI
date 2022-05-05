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

  public void placePiece(Piece p){
    p.moveTo(rank, file);
    piece = p;
  }

  public void capture() {
    if (piece.isWhite()) {
      for (int i = 0; i < Board.getWhitePieces().size(); i++) {
        if (Board.getWhitePieces().get(i) == piece) {
          Board.getWhitePieces().remove(i);
        }
      }
    } else {
      for (int i = 0; i < Board.getBlackPieces().size(); i++) {
        if (Board.getBlackPieces().get(i) == piece) {
          Board.getBlackPieces().remove(i);
        }
      }
    }
    piece = null;
  }

  public void removePiece() {
    piece = null;
  }

  public void draw(Graphics g) {
    // if (isWhite) g.setColor(Color.WHITE);
    // else g.setColor(Color.BLACK);
    if (isWhite) g.setColor(new Color(210, 180, 140));
    else g.setColor(new Color(101, 67, 33));
    g.fillRect(rank*side, file*side, side, side);
    
  }

  public static int getSide() {
    return side;
  }
}