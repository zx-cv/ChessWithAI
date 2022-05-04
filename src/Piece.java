package src;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public abstract class Piece{
  protected boolean isWhite;
  protected int rank,file;
  private static Image spriteSheet;
  private boolean selected;
  private Image image;

  //returns a list of legal squares it can move to
  public abstract ArrayList<Square> getLegalMoves(Board board);

  public void setImage(Image i) {
    if (image == null) image = i;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public void moveTo(int r, int f) {
    this.rank = r;
    this.file = f;
  }

  public void select(boolean s) {
    selected = s;
  }

  public boolean isSelected() {
    return selected;
  }

  protected static Image openImageFromSpriteSheet(int x, int y, int w, int h) {
		openSpriteSheet();
		return ((BufferedImage)spriteSheet).getSubimage(x,y,w,h).getScaledInstance(Square.getSide(), Square.getSide(), BufferedImage.SCALE_SMOOTH);
	}

  private static void openSpriteSheet() {
		if(spriteSheet==null) {
			try {
				File f = new File("./images/Chess_SpriteSheet.png");
				spriteSheet = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

  public void draw(Graphics g) {
    int x = file * Square.getSide();
    int y = rank * Square.getSide();
    int w = Square.getSide();
    int h = Square.getSide();
    g.drawImage(image, x, y,null);
    if (selected) {
      g.setColor(new Color(255, 255, 0, 75)); //75 = opacity
      g.fillRect(x, y, w, h);
    }
  }
}