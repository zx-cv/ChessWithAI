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
  protected int value;
  private String type;
  
  public abstract String type();
  //returns a list of legal squares it can move to
  public abstract ArrayList<Square> getLegalMoves(Board board);
  public boolean isPawn() {
    return false;
  }
  public void setImage(Image i) {
    if (image == null) image = i;
  }
  public Image getImage() {
    return image;
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

  public int getRank() {
    return rank;
  }

  public int getFile() {
    return file;
  }

  public int getValue() {
    return value;
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
    int x = file * Square.getSide()+2*Square.getSide();
    int y = rank * Square.getSide()+Square.getSide()/2;
    int w = Square.getSide();
    int h = Square.getSide();
    g.drawImage(image, x, y,null);
    if (selected) {
      g.setColor(new Color(255, 255, 0, 75)); //75 = opacity
      g.fillRect(x, y, w, h);
    }
  }

  public void drawCaptured(Graphics g, int x, int y) {
    g.drawImage(image, x, y, 20, 20, null);
  }
}