package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Board {
  private Square[][] grid = new Square[8][8];
  private static ArrayList<Piece> whitePieces = new ArrayList<>();
  private static ArrayList<Piece> blackPieces = new ArrayList<>();
  private int lastClickedR = -1, lastClickedC = -1;
  private boolean secondClick = false;
  private boolean isWhite;
  private boolean moveWhite = true;
  private King bKing, wKing;
  private ArrayList<Square[][]> boardStates = new ArrayList<>();

  public Board(boolean isWhite) {
    this.isWhite = isWhite;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((i % 2 + j % 2) % 2 == 0) {
          grid[i][j] = new Square(true, i, j);
        } else {
          grid[i][j] = new Square(false, i, j);
        }
      }
    }
    generatePieces(isWhite);
  }

  public ArrayList<Square[][]> getBoardStates() {
    return boardStates;
  }

  public Square[][] getGrid() {
    return grid;
  }

  public static ArrayList<Piece> getWhitePieces() {
    return whitePieces;
  }

  public static ArrayList<Piece> getBlackPieces() {
    return blackPieces;
  }

  // initializes and place pieces on the board
  // isWhite == true will make white on bottom and vice versa
  private void generatePieces(boolean isWhite) {
    // Pawns
    for (int i = 0; i < 8; i++) {
      grid[1][i].placePiece(new Pawn(!isWhite, 1, i));
      grid[6][i].placePiece(new Pawn(isWhite, 6, i));
    }

    // Rooks
    grid[0][0].placePiece(new Rook(!isWhite, 0, 0));
    grid[0][7].placePiece(new Rook(!isWhite, 0, 7));
    grid[7][0].placePiece(new Rook(isWhite, 7, 0));
    grid[7][7].placePiece(new Rook(isWhite, 7, 7));

    // Knights
    grid[0][1].placePiece(new Knight(!isWhite, 0, 1));
    grid[0][6].placePiece(new Knight(!isWhite, 0, 6));
    grid[7][1].placePiece(new Knight(isWhite, 7, 1));
    grid[7][6].placePiece(new Knight(isWhite, 7, 6));

    // Bishops
    grid[0][2].placePiece(new Bishop(!isWhite, 0, 2));
    grid[0][5].placePiece(new Bishop(!isWhite, 0, 5));
    grid[7][2].placePiece(new Bishop(isWhite, 7, 2));
    grid[7][5].placePiece(new Bishop(isWhite, 7, 5));

    // King and Queen
    if (isWhite) {
      grid[0][3].placePiece(new Queen(false, 0, 3));

      bKing = new King(false, 0, 4);
      grid[0][4].placePiece(bKing);

      grid[7][3].placePiece(new Queen(true, 7, 3));

      wKing = new King(true, 7, 4);
      grid[7][4].placePiece(wKing);
    } else {
      wKing = new King(true, 0, 3);
      grid[0][3].placePiece(wKing);

      grid[0][4].placePiece(new Queen(true, 0, 4));

      bKing = new King(false, 7, 3);
      grid[7][3].placePiece(bKing);

      grid[7][4].placePiece(new Queen(false, 7, 4));
    }

    // adding each piece to blackPieces/whitePieces
    if (isWhite) {
      for (int i = 0; i < 8; i++) {
        blackPieces.add(grid[0][i].getPiece());
        blackPieces.add(grid[1][i].getPiece());
        whitePieces.add(grid[6][i].getPiece());
        whitePieces.add(grid[7][i].getPiece());
      }
    } else {
      for (int i = 0; i < 8; i++) {
        whitePieces.add(grid[0][i].getPiece());
        whitePieces.add(grid[1][i].getPiece());
        blackPieces.add(grid[6][i].getPiece());
        blackPieces.add(grid[7][i].getPiece());
      }
    }
  }

  public boolean isWhite() {
    return isWhite;
  }

  public void justClicked(MouseEvent me) {
    int r = (me.getY() - Square.getSide() / 2) / Square.getSide();
    int c = (me.getX() - 2 * Square.getSide()) / Square.getSide();
    if (!secondClick) {
      if (grid[r][c].getPiece() == null) {
        return;
      }
      if (grid[r][c].getPiece().isWhite != moveWhite) {
        return;
      }
      secondClick = true;
      lastClickedR = r;
      lastClickedC = c;
      grid[r][c].getPiece().select(true);
      return;
    }

    grid[lastClickedR][lastClickedC].getPiece().select(false);
    if (!grid[lastClickedR][lastClickedC].getPiece().getLegalMoves(this).contains(grid[r][c])
        || (grid[r][c] == grid[lastClickedR][lastClickedC])) {
      secondClick = false;
      return;
    }

    if (!grid[lastClickedR][lastClickedC].hasPiece()) { // || !validMoves.contains(grid[r][c])
      System.out.println("Invalid move!");
      return;
    }
    GameFrame.startTime = System.currentTimeMillis();

    Piece p = null;
    if (grid[r][c].hasPiece()) {
      p = grid[r][c].getPiece();
      grid[r][c].capture();
    }

    Piece movingPiece = grid[lastClickedR][lastClickedC].getPiece();
    grid[r][c].placePiece(movingPiece);
    grid[lastClickedR][lastClickedC].removePiece();

    // if the side that just moved is still in check, undo the move
    if ((moveWhite && whiteInCheck()) || (!moveWhite && blackInCheck())) {
      grid[lastClickedR][lastClickedC].placePiece(movingPiece);
      grid[r][c].removePiece();
      if (p != null) {
        grid[r][c].placePiece(p);
        if (p instanceof Rook) {
          ((Rook) p).subtractMove();
        } else if (p instanceof King) {
          ((King) p).subtractMove();
        }
        if (p.isWhite()) {
          whitePieces.add(p);
        } else {
          blackPieces.add(p);
        }
      }
      secondClick = false;
      if (movingPiece instanceof Rook) {
        ((Rook) movingPiece).subtractMove();
      } else if (movingPiece instanceof King) {
        ((King) movingPiece).subtractMove();
      }
      return;
    }

    lastClickedC = -1;
    lastClickedR = -1;
    secondClick = false;
    moveWhite = !moveWhite;


    if (blackInCheck() || whiteInCheck()) {
      if (whiteCheckMated()) {
        System.out.println("White Checkmated");
      } else if (blackCheckMated()) {
        System.out.println("Black Checkmated");
      } else {
        System.out.println("CHECK");
      }
    }

    else if ((moveWhite && whiteCheckMated()) || (!moveWhite && blackCheckMated())) {
      System.out.println("Stalemate");
    }

    System.out.println(canQueenSideCastle(false));

    boardStates.add(grid.clone());
  }

  public boolean inBounds(int toR, int toC) {
    return toR >= 0 && toR < grid.length && toC >= 0 && toC < grid[0].length;
  }

  public void draw(Graphics g) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        grid[i][j].draw(g);
      }
    }
    // g.setColor(new Color(255, 255, 0, 75));
    g.setColor(Color.YELLOW);
    int x = 22;
    if (moveWhite) {
      x = 518;
    }
    g.fillRect(x, 50, 50, 25);
    g.setColor(Color.YELLOW);
    g.drawRect(x, 50, 50, 25);

    g.setColor(Color.WHITE);
    if (moveWhite) {
      GameFrame.btime.setVisible(false);
      GameFrame.wtime.setVisible(true);
    } else {
      GameFrame.wtime.setVisible(false);
      GameFrame.btime.setVisible(true);
    }
  }

  public boolean blackInCheck() {
    Square kSq = grid[bKing.getRank()][bKing.getFile()];
    for (Piece p : whitePieces) {
      ArrayList<Square> moves;
      if (p instanceof Pawn) {
        moves = ((Pawn) p).getAttackMoves(this);
      } else {
        moves = p.getLegalMoves(this);
      }

      if (moves.contains(kSq)) {
        return true;
      }
    }
    return false;
  }

  public boolean whiteInCheck() {
    Square kSq = grid[wKing.getRank()][wKing.getFile()];
    for (Piece p : blackPieces) {
      ArrayList<Square> moves;
      if (p instanceof Pawn) {
        moves = ((Pawn) p).getAttackMoves(this);
      } else {
        moves = p.getLegalMoves(this);
      }

      if (moves.contains(kSq)) {
        return true;
      }
    }
    return false;
  }

  public boolean whiteCheckMated() {
    for (Piece p : whitePieces) {
      ArrayList<Square> moves = p.getLegalMoves(this);
      for (Square s : moves) {
        if (testMove(p, s)) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean blackCheckMated() {
    for (Piece p : blackPieces) {
      ArrayList<Square> moves = p.getLegalMoves(this);
      for (Square s : moves) {
        if (testMove(p, s)) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean testMove(Piece piece, Square s) {
    int fromR = piece.getRank();
    int fromC = piece.getFile();
    int toR = s.getRank();
    int toC = s.getFile();
    Piece p = null;
    boolean ans = true;
    if (grid[toR][toC].hasPiece()) {
      p = grid[toR][toC].getPiece();
      grid[toR][toC].capture();
    }

    Piece movingPiece = grid[fromR][fromC].getPiece();
    grid[toR][toC].placePiece(movingPiece);
    grid[fromR][fromC].removePiece();

    if ((moveWhite && whiteInCheck()) || (!moveWhite && blackInCheck())) {
      ans = false;
    }

    grid[fromR][fromC].placePiece(movingPiece);
    grid[toR][toC].removePiece();
    if (movingPiece instanceof Rook) {
      ((Rook) movingPiece).subtractMove();
      ((Rook) movingPiece).subtractMove();
    } else if (movingPiece instanceof King) {
      ((King) movingPiece).subtractMove();
      ((King) movingPiece).subtractMove();
    }
    if (p != null) {
      grid[toR][toC].placePiece(p);
      if (p.isWhite()) {
        whitePieces.add(p);
      } else {
        blackPieces.add(p);
      }
      if (p instanceof Rook) {
        ((Rook) p).subtractMove();
        ((Rook) p).subtractMove();
      } else if (p instanceof King) {
        ((King) p).subtractMove();
        ((King) p).subtractMove();
      }
    }
    return ans;
  }

  public void queenSideCastle(boolean whiteSide) {
    if (whiteSide) {
      if (isWhite) {
        grid[7][2].placePiece(wKing);
        grid[7][4].removePiece();
        grid[7][3].placePiece(grid[7][0].getPiece());
        grid[7][0].removePiece();
      }
    }
  }

  public boolean canQueenSideCastle(boolean whiteSide) {
    ArrayList<Square> moves = new ArrayList<>();
    if (whiteSide) {
      // get all black moves
      for (Piece p : blackPieces) {
        if (p instanceof Pawn) {
          moves.addAll(((Pawn) p).getAttackMoves(this));
        } else {
          moves.addAll(p.getLegalMoves(this));
        }
      }
      int row;
      int startCol;
      int rFile;
      int kFile;
      if (isWhite) {
        row = 7;
        rFile = 0;
        startCol = 2;
        kFile = 1;
      } else {
        row = 0;
        rFile = 7;
        startCol = 4;
        kFile = 6;
      }
      // if the king/rook has moved or there is still a piece between them then return
      // false
      if (wKing.moved() || whiteInCheck() || !grid[row][rFile].hasPiece()
          || !(grid[row][rFile].getPiece() instanceof Rook) || ((Rook) grid[row][rFile].getPiece()).moved()
          || grid[row][kFile].hasPiece()) {
        return false;
      }

      // if any of the squares that is the king through is in check then return false
      for (int i = startCol; i < startCol + 2; i++) {
        if (grid[row][i].hasPiece() || moves.contains(grid[row][i])) {
          return false;
        }
      }

      return true;

    } else {
      // get all black moves
      for (Piece p : whitePieces) {
        if (p instanceof Pawn) {
          moves.addAll(((Pawn) p).getAttackMoves(this));
        } else {
          moves.addAll(p.getLegalMoves(this));
        }
      }
      int row;
      int startCol;
      int rFile;
      int kFile;
      if (isWhite) {
        row = 0;
        rFile = 0;
        startCol = 2;
        kFile = 1;
      } else {
        row = 7;
        rFile = 7;
        startCol = 4;
        kFile = 6;
      }
      // if the king/rook has moved or there is still a piece between them then return
      // false
      if (bKing.moved() || blackInCheck() || !grid[row][rFile].hasPiece()
          || !(grid[row][rFile].getPiece() instanceof Rook) || ((Rook) grid[row][rFile].getPiece()).moved()
          || grid[row][kFile].hasPiece()) {
        return false;
      }

      // if any of the squares that is the king through is in check then return false
      for (int i = startCol; i < startCol + 2; i++) {
        if (grid[row][i].hasPiece() || moves.contains(grid[row][i])) {
          return false;
        }
      }

      return true;
    }
  }
}