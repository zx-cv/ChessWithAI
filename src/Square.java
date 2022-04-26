public Class Square{
  
  private boolean isWhite;
  private Piece piece;

  public Square(boolean isWhite){
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

}