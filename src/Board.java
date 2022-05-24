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
  private static boolean isWhite = true;
  public static boolean moveWhite = true;
  private boolean promoteClick = false;
  private King bKing, wKing;
  private ArrayList<Square[][]> boardStates = new ArrayList<>();
  private Square ghostPawn = null;
  private static ArrayList<Piece> whiteCaptured = new ArrayList<>();
  private static ArrayList<Piece> blackCaptured = new ArrayList<>();
  private int afterGPawn;

  public Board() {
    //setWhite(isWhite);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((i % 2 + j % 2) % 2 == 0) {
          grid[i][j] = new Square(true, i, j);
        } else {
          grid[i][j] = new Square(false, i, j);
        }
      }
    }
    //generatePieces(isWhite);
    
  }

  public boolean whiteMove() {
    return moveWhite;
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
  public void generatePieces(boolean isWhite) {
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
      grid[0][3].placePiece(new Queen(!isWhite, 0, 3));

      bKing = new King(!isWhite, 0, 4);
      grid[0][4].placePiece(bKing);

      grid[7][3].placePiece(new Queen(isWhite, 7, 3));

      wKing = new King(isWhite, 7, 4);
      grid[7][4].placePiece(wKing);
    } else {
      bKing = new King(!isWhite, 0, 3);
      grid[0][3].placePiece(bKing);

      grid[0][4].placePiece(new Queen(!isWhite, 0, 4));

      wKing = new King(isWhite, 7, 3);
      grid[7][3].placePiece(wKing);

      grid[7][4].placePiece(new Queen(isWhite, 7, 4));
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

  public static boolean isWhite() {
    return isWhite;
  }

  public static void setWhite(boolean b) {
    isWhite = b;
  }

  public void startClicked(MouseEvent me) {
    
  }

  public void justClicked(MouseEvent me) {
    int r = (me.getY() - Square.getSide()/2) / Square.getSide();
    int c = (me.getX() - 2*Square.getSide()) / Square.getSide();
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

    if (promoteClick) {

      Piece p = null;
      switch (Math.abs(r - lastClickedR)) {
        case 0:
          p = moveWhite?new Queen(moveWhite, r, c - 1):new Knight(moveWhite, r, c - 1);
          break;
        case 1:
          p = moveWhite?new Rook(moveWhite, r, c - 1):new Bishop(moveWhite, r, c - 1);
          break;
        case 2:
          p = moveWhite?new Bishop(moveWhite, r, c - 1):new Rook(moveWhite, r, c - 1);
          break;
        case 3:
          p = moveWhite?new Knight(moveWhite, r, c - 1):new Queen(moveWhite, r, c - 1);
          break;
      }
      if (moveWhite) {
        whitePieces.remove(grid[lastClickedR][lastClickedC].getPiece());
        whitePieces.add(p);
      } else {
        blackPieces.remove(grid[lastClickedR][lastClickedC].getPiece());
        blackPieces.add(p);
      }
      grid[lastClickedR][lastClickedC].removePiece();
      grid[lastClickedR][lastClickedC].placePiece(p);
      promoteClick = !promoteClick;
      secondClick = !secondClick;
      moveWhite = !moveWhite;
      GameFrame.pawnPromotion.setVisible(false);
      GameFrame.pawnPromotion.repaint();
      return;
    }
    grid[lastClickedR][lastClickedC].getPiece().select(false);
    if (grid[lastClickedR][lastClickedC].getPiece() instanceof King) {
      if (moveWhite) {
        if (lastClickedR == r) {
          if (lastClickedC - c == 2) {
            if (isWhite && canQueenSideCastle(true)) {
              queenSideCastle(true);
              return;
            } else if (canKingSideCastle(true)) {
              kingSideCastle(true);
              return;
            }
          } else if (lastClickedC - c == -2) {
            if (isWhite && canKingSideCastle(true)) {
              kingSideCastle(true);
              return;
            } else if (canQueenSideCastle(true)) {
              queenSideCastle(true);
              return;
            }
          }
        }
      } else {
        if (lastClickedR == r) {
          if (lastClickedC - c == 2) {
            if (isWhite && canQueenSideCastle(false)) {
              queenSideCastle(false);
              return;
            } else if (canKingSideCastle(false)) {
              kingSideCastle(false);
              return;
            }
          } else if (lastClickedC - c == -2) {
            if (isWhite && canKingSideCastle(false)) {
              kingSideCastle(false);
              return;
            } else if (canQueenSideCastle(false)) {
              queenSideCastle(false);
              return;
            }
          }
        }
      }
    }
    if (!grid[lastClickedR][lastClickedC].getPiece().getLegalMoves(this).contains(grid[r][c])
        || (grid[r][c] == grid[lastClickedR][lastClickedC])) {
      secondClick = false;
      return;
    }

    if (!grid[lastClickedR][lastClickedC].hasPiece()) {
      System.out.println("Invalid move!");
      return;
    }
    // GameFrame.startTime = System.currentTimeMillis();

    //if the piece moved was a pawn that jumped a square, put a ghost pawn on the jumped square
    if (grid[lastClickedR][lastClickedC].getPiece().isPawn() && Math.abs(r - lastClickedR) == 2) {
      if (ghostPawn != null && ghostPawn.hasPiece())
        ghostPawn.capture();
      ghostPawn = grid[(r + lastClickedR) / 2][c];
      ghostPawn.placePiece(new Pawn(moveWhite, (r + lastClickedR) / 2, c));
      afterGPawn = 0;
    }

    //is the gPawn isn't null and a pawn is trying to move to the gPawn square, take the pawn connected to it
    else if (ghostPawn != null && ghostPawn == grid[r][c] && grid[lastClickedR][lastClickedC].getPiece().isPawn()) {
      int dir = (isWhite ^ ghostPawn.getPiece().isWhite()) ? 1 : -1;
      addCapturedPiece(grid[ghostPawn.getRank() + dir][ghostPawn.getFile()].getPiece());
      grid[ghostPawn.getRank() + dir][ghostPawn.getFile()].capture();
      ghostPawn.capture();
      ghostPawn = null;
    }

    // if gPawn square isn't null and a turn has passed without claiming en passant, delete the gPawn
    if (ghostPawn != null && afterGPawn == 1) {
      ghostPawn.capture();
      ghostPawn = null;
    }

    Piece p = null;
    if (grid[r][c].hasPiece()) {
      p = grid[r][c].getPiece();
      addCapturedPiece(p);
      grid[r][c].capture();
    }

    Piece movingPiece = grid[lastClickedR][lastClickedC].getPiece();
    grid[r][c].placePiece(movingPiece);
    grid[lastClickedR][lastClickedC].removePiece();

    // if the side that just moved is still in check, undo the move
    if ((moveWhite && whiteInCheck()) || (!moveWhite && blackInCheck())) {
      grid[lastClickedR][lastClickedC].placePiece(movingPiece);
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
    }
    
    if (grid[r][c].getPiece().isPawn() && r == ((Pawn) grid[r][c].getPiece()).endrow) {
      promoteClick = true;
      lastClickedC = c;
      lastClickedR = r;
      
      return;
    }
    if (movingPiece instanceof Rook) {
      ((Rook) movingPiece).subtractMove();
      return;
    } else if (movingPiece instanceof King) {
      ((King) movingPiece).subtractMove();
      return;
    }

    if (grid[r][c].getPiece().isPawn() && r == ((Pawn) grid[r][c].getPiece()).endrow) {
      promoteClick = true;
      lastClickedC = c;
      lastClickedR = r;
    }

    lastClickedC = -1;
    lastClickedR = -1;
    secondClick = false;
    moveWhite = !moveWhite;

    if (blackInCheck() || whiteInCheck()) {
      if (whiteCheckMated()) {
        System.out.println("White Checkmated");
        Game.gameOver = true;
      } else if (blackCheckMated()) {
        System.out.println("Black Checkmated");
        Game.gameOver = true;
      } else {
        System.out.println("CHECK");
      }
    }

    else if ((moveWhite && whiteCheckMated()) || (!moveWhite && blackCheckMated())) {
      System.out.println("Stalemate");
      Game.gameOver = true;
    }

    afterGPawn++;

    boardStates.add(grid.clone());
  }

  public boolean inBounds(int toR, int toC) {
    return toR >= 0 && toR < grid.length && toC >= 0 && toC < grid[0].length;
  }

  public void addCapturedPiece(Piece p) {
    if (p.isWhite) whiteCaptured.add(p);
    else blackCaptured.add(p);
  }

  public void draw(Graphics g) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        grid[i][j].draw(g);
      }
    }
    //g.setColor(new Color(255, 255, 0, 75));
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

    Collections.sort(whiteCaptured, new comp());
    for (int i = 0; i < whiteCaptured.size(); i++) {
      whiteCaptured.get(i).drawCaptured(g, 35, 75+i*20);
      
    }
    Collections.sort(blackCaptured, new comp());
    for (int i = 0; i < blackCaptured.size(); i++) {
      blackCaptured.get(i).drawCaptured(g, 535, 75+i*20);
    }

  }
  static class comp implements Comparator <Piece> { 
    public int compare(Piece p1, Piece p2) {
      if (p1.getValue()>p2.getValue()) 
        return -1;
      return 1;
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
    for (Piece p: whitePieces) {
      ArrayList<Square> moves = p.getLegalMoves(this);
      for(Square s: moves) {
        if (testMove(p, s)) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean blackCheckMated() {
    for (Piece p: blackPieces) {
      ArrayList<Square> moves = p.getLegalMoves(this);
      for(Square s: moves) {
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

    if (movingPiece instanceof Rook) {
      ((Rook) movingPiece).subtractMove();
      ((Rook) movingPiece).subtractMove();
    } else if (movingPiece instanceof King) {
      ((King) movingPiece).subtractMove();
      ((King) movingPiece).subtractMove();
    }

    if ((moveWhite && whiteInCheck()) || (!moveWhite && blackInCheck())) {
      ans = false;
    }

    grid[fromR][fromC].placePiece(movingPiece);
    grid[toR][toC].removePiece();
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
      } else {
        grid[0][5].placePiece(wKing);
        grid[0][3].removePiece();
        grid[0][4].placePiece(grid[0][7].getPiece());
        grid[0][7].removePiece();
      }
    } else {
      if (isWhite) {
        grid[0][2].placePiece(bKing);
        grid[0][4].removePiece();
        grid[0][3].placePiece(grid[0][0].getPiece());
        grid[0][0].removePiece();
      } else {
        grid[7][5].placePiece(bKing);
        grid[7][3].removePiece();
        grid[7][4].placePiece(grid[7][7].getPiece());
        grid[7][7].removePiece();
      }
    }
    lastClickedC = -1;
    lastClickedR = -1;
    secondClick = false;
    moveWhite = !moveWhite;
  }

  public void kingSideCastle(boolean whiteSide) {
    if (whiteSide) {
      if (isWhite) {
        grid[7][6].placePiece(wKing);
        grid[7][4].removePiece();
        grid[7][5].placePiece(grid[7][7].getPiece());
        grid[7][7].removePiece();
      } else {
        grid[0][1].placePiece(wKing);
        grid[0][3].removePiece();
        grid[0][2].placePiece(grid[0][0].getPiece());
        grid[0][0].removePiece();
      }
    } else {
      if (isWhite) {
        grid[0][6].placePiece(bKing);
        grid[0][4].removePiece();
        grid[0][5].placePiece(grid[0][7].getPiece());
        grid[0][7].removePiece();
      } else {
        grid[7][1].placePiece(bKing);
        grid[7][3].removePiece();
        grid[7][2].placePiece(grid[7][0].getPiece());
        grid[7][0].removePiece();
      }
    }
    lastClickedC = -1;
    lastClickedR = -1;
    secondClick = false;
    moveWhite = !moveWhite;
  }

  public boolean canQueenSideCastle(boolean whiteSide) {
    ArrayList<Square> moves = new ArrayList<>();
    ArrayList<Piece> pieces;
    int row;
    King king;
    boolean inCheck;
    if (whiteSide) {
      pieces = blackPieces;
      row = isWhite ? 7 : 0;
      king = wKing;
      inCheck = whiteInCheck();
    } else {
      pieces = whitePieces;
      row = isWhite ? 0 : 7;
      king = bKing;
      inCheck = blackInCheck();
    }

    // get all opposite side moves
    for (Piece p : pieces) {
      if (p instanceof Pawn) {
        moves.addAll(((Pawn) p).getAttackMoves(this));
      } else {
        moves.addAll(p.getLegalMoves(this));
      }
    }
    int startCol;
    int rFile;
    int kFile;
    if (isWhite) {
      rFile = 0;
      startCol = 2;
      kFile = 1;
    } else {
      rFile = 7;
      startCol = 4;
      kFile = 6;
    }
    // if the king/rook has moved or there is still a piece between them then return
    // false
    if (king.moved() || inCheck || !grid[row][rFile].hasPiece()
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

  public boolean canKingSideCastle(boolean whiteSide) {
    ArrayList<Square> moves = new ArrayList<>();
    ArrayList<Piece> pieces;
    int row;
    King king;
    boolean inCheck;
    if (whiteSide) {
      pieces = blackPieces;
      row = isWhite ? 7 : 0;
      king = wKing;
      inCheck = whiteInCheck();
    } else {
      pieces = whitePieces;
      row = isWhite ? 0 : 7;
      king = bKing;
      inCheck = blackInCheck();
    }
    // get all black moves
    for (Piece p : pieces) {
      if (p instanceof Pawn) {
        moves.addAll(((Pawn) p).getAttackMoves(this));
      } else {
        moves.addAll(p.getLegalMoves(this));
      }
    }
    int startCol;
    int rFile;
    if (isWhite) {
      rFile = 7;
      startCol = 5;
    } else {
      rFile = 0;
      startCol = 1;
    }
    // if the king/rook has moved or there is still a piece between them then return
    // false
    if (king.moved() || inCheck || !grid[row][rFile].hasPiece()
        || !(grid[row][rFile].getPiece() instanceof Rook) || ((Rook) grid[row][rFile].getPiece()).moved()) {
      return false;
    }

    // if any of the squares that the king goes through is in check then return
    // false
    for (int i = startCol; i < startCol + 2; i++) {
      if (grid[row][i].hasPiece() || moves.contains(grid[row][i])) {
        return false;
      }
    }

    return true;
  }
}