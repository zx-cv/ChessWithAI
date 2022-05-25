package src;

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Pawn extends Piece{
    private static int x=450, w = 90, h=90;
    private int y = 0;
    private boolean firstMove = true;
    private boolean secondMove = true;
    public int endrow = 0;

    public Pawn(boolean isWhite, int rank, int file) {
        this.isWhite = isWhite;
        if (!this.isWhite)  {y = 90; endrow = 7;}
        this.rank = rank;
        this.file = file;
        this.setImage(openImageFromSpriteSheet(x, y, w, h));
        this.value = 1;
    }
    
    @Override
    public boolean isPawn() {
        return true;
    }
    //Doesn't account for checks
    public ArrayList<Square> getLegalMoves(Board b) {
        int dir = (isWhite ^ b.isWhite()) ? 1 : -1;
        Square[][] board = b.getGrid();
        ArrayList<Square> ans = new ArrayList<>();
        if (firstMove) {
            if (!board[rank+dir][file].hasPiece() && !board[rank+(2*dir)][file].hasPiece()) {
                ans.add(board[rank+(2*dir)][file]);
            }
        }
        if ((rank+dir >= 0 && rank+dir <= 7) && !board[rank+dir][file].hasPiece()) {
            ans.add(board[rank+dir][file]);
        }
        for (int f = file - 1; f < file + 2; f += 2) {
            if (f < 0 || f > 7 || rank+dir < 0 || rank+dir > 7) {
                continue;
            }
            if (board[rank+dir][f].hasPiece() && board[rank+dir][f].getPiece().isWhite() != isWhite) {
                ans.add(board[rank+dir][f]);
            }
            // if (ddboard[rank][f].hasPiece() && board[rank][f].getPiece().isPawn() && ((Pawn) board[rank][f].getPiece()).secondMove && (board[rank][f].getPiece().isWhite() != this.isWhite)) {
            //     ans.add(board[rank+dir][f]);
            // }
        }
        return ans;
    }

    public ArrayList<Square> getAttackMoves(Board b) {
        ArrayList<Square> ans = getLegalMoves(b);


        for (int i = 0; i < ans.size(); i++) {
            if (ans.get(i).getFile() == file) {
                ans.remove(i);
            }
        }

        return ans;
    }
    @Override
    public void moveTo(int r, int f) {
        super.moveTo(r, f);
        if (firstMove && secondMove) {
            firstMove = true;
            secondMove = false;
        } else if (firstMove) {
            firstMove = false;
            secondMove = true;
        } else if (secondMove) {
            secondMove = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (rank != endrow) {return;}
        GameFrame.getPawnPromotion().setVisible(true);
        GameFrame.getPawnPromotion().setBackground(Color.WHITE);
        GameFrame.getPawnPromotion().setOpaque(true);
        
        if (isWhite) {
            GameFrame.getPawnPromotion().setBounds(this.file*Square.getSide()+3*Square.getSide(), y+Square.getSide()/2, Square.getSide(), 4*Square.getSide());
            ImageIcon q = new ImageIcon(openImageFromSpriteSheet(Queen.getX(), 0, w, h)); 
            JLabel qlabel = new JLabel();
            qlabel.setIcon(q);
            GameFrame.getPawnPromotion().add(qlabel);
            ImageIcon r = new ImageIcon(openImageFromSpriteSheet(Rook.getX(), 0, w, h)); 
            JLabel rlabel = new JLabel();
            rlabel.setIcon(r);
            GameFrame.getPawnPromotion().add(rlabel);
            ImageIcon b = new ImageIcon(openImageFromSpriteSheet(Bishop.getX(), 0, w, h)); 
            JLabel blabel = new JLabel();
            blabel.setIcon(b);
            GameFrame.getPawnPromotion().add(blabel);
            ImageIcon k = new ImageIcon(openImageFromSpriteSheet(Knight.getX(), 0, w, h)); 
            JLabel klabel = new JLabel();
            klabel.setIcon(k);
            GameFrame.getPawnPromotion().add(klabel);
        } else {
            GameFrame.getPawnPromotion().setBounds(this.file*Square.getSide()+3*Square.getSide(), this.rank*Square.getSide()-2*Square.getSide()-Square.getSide()/2, Square.getSide(), 4*Square.getSide());
            ImageIcon q = new ImageIcon(openImageFromSpriteSheet(Queen.getX(), 90, w, h)); 
            JLabel qlabel = new JLabel();
            qlabel.setIcon(q);
            GameFrame.getPawnPromotion().add(qlabel);
            ImageIcon r = new ImageIcon(openImageFromSpriteSheet(Rook.getX(), 90, w, h)); 
            JLabel rlabel = new JLabel();
            rlabel.setIcon(r);
            GameFrame.getPawnPromotion().add(rlabel);
            ImageIcon b = new ImageIcon(openImageFromSpriteSheet(Bishop.getX(), 90, w, h)); 
            JLabel blabel = new JLabel();
            blabel.setIcon(b);
            GameFrame.getPawnPromotion().add(blabel);
            ImageIcon k = new ImageIcon(openImageFromSpriteSheet(Knight.getX(), 90, w, h)); 
            JLabel klabel = new JLabel();
            klabel.setIcon(k);
            GameFrame.getPawnPromotion().add(klabel);
        }
        
       
        //g.drawImage(openImageFromSpriteSheet(Queen.x, iy, w, h), x, y+Square.getSide()/2, null);
        //g.drawImage(openImageFromSpriteSheet(Rook.x, iy, w, h), x, y+Square.getSide()+Square.getSide()/2, null);
        //g.drawImage(openImageFromSpriteSheet(Bishop.x, iy, w, h), x, y+2*Square.getSide()+Square.getSide()/2, null);
        //g.drawImage(openImageFromSpriteSheet(Knight.x, iy, w, h), x, y+3*Square.getSide()+Square.getSide()/2, null);
        
        GameFrame.getPawnPromotion().repaint();
    }
    
}