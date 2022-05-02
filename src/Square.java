package src;
import java.awt.*;

public class Square{
  
  private boolean isWhite;
  private Piece piece;
  private static int side = 50;
  private int r, c;

  public Square(boolean isWhite, int r, int c){
    this.r = r;
    this.c = c;
    this.isWhite = isWhite;
  }

  public boolean hasPiece(){
    return piece != null;
  }

  public Piece getPiece(){
    return piece;
  }

  public void placePiece(Piece p){
    piece = p;
  }

  public void draw(Graphics g) {
    // if (isWhite) g.setColor(Color.WHITE);
    // else g.setColor(Color.BLACK);
    if (isWhite) g.setColor(new Color(210, 180, 140));
    else g.setColor(new Color(101, 67, 33));
    g.fillRect(r, c, side, side);
    
  }

  public static int getSide() {
    return side;
  }
}