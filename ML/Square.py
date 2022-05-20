import numpy as np

class Square:
    def __init__(self, isWhite, rank, file):
        self.isWhite = isWhite
        self.rank = rank
        self.file = file
        self.piece = None
        self. side = 50
    
    def hasPiece():
        return piece != None
    
    def getPiece():
        return piece
    
    def getRank():
        return rank
    
    def getFile():
        return file

    def capture():
        if piece.isWhite():
            for i in range(Board.getWhitePieces().size):
                if Board.getWhitePieces()[i] == piece:
                    Board.getWhitePieces() = np.delete(Board.getWhitePieces(), i)
        else:
            for i in range(Board.getBlackPieces().size):
                if Board.getBlackPieces()[i] == piece:
                    Board.getBlackPieces() = np.delete(Board.getBlackPieces(), i)
        removePiece()
    
    def removePiece():
        piece = None
    
    def getSide():
        return side

class Piece():
    def __init__(self, isWhite, rank, file):
        self.isWhite = isWhite
        self.rank = rank
        self.file = file
        self.isPawn = False
    
    def getLegalMoves(self, board):
        pass

    def isPawn():
        return False
    
    def isWhite():
        return isWhite

    def moveTo(self, r, f):
        self.rank = r
        self.file = f
    
    def getRank(self):
        return self.rank
    
    def getFile(self):
        return self.file

class Pawn(Piece):
    def __init__(self, isWhite, rank, file):
        super(isWhite, rank, file)
        self.firstMove = True
        self.secondMove = True
        self.isPawn = True
    
    @Override
    def isPawn():
        return True
    
    def getLegalMoves(self, b):
        dir = 0
        if self.isWhite ^ b.isWhite():
            dir = 1
        else:
            dir = -1
        
        board = b.getGrid()
        arr = np.array([])
        if firstMove:
            if (not board[self.rank+dir][self.file].hasPiece() and not board[rank+(2*dir)][self.file]):
                ans.add(board[self.rank+2*dir][self.file])
        if not board[self.rank+dir][self.file].hasPiece():
            ans.add(board[self.rank+dir][self.file])
        for f in [self.file-1,self.file+1]:
            if (f<0 or f>7):
                continue
            if (board[self.rank+dir][f].hasPiece() and board[self.rank+dir][f].getPiece().isWhite != self.isWhite):
                ans.add(board[self.rank+dir][f])
        return ans
    
    def getAttackMoves(self, b):
        ans = getLegalMoves(b)
        for i in ans:
            if ans.get(i).getFile() == self.file:
                ans.remove(i)
        
        return ans
    
    def moveTo(self, r, f):
        super.moveTo(r, f)
        if (self.firstMove and self.secondMove):
            self.firstMove = True
            self.secondMove = False
        elif self.firstMove:
            self.firstMove = False
            self.secondMove = True
        elif self.secondMove:
            self.secondMove = False
    
class Bishop(Piece):
    def __init__(self, isWhite, rank, file):
        super(isWhite, rank, file)
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = np.array([])

        r = self.rank
        f = self.file
        while (r>0 and f>0):
            r-=1
            f-=1
            if board[r][f].hasPiece():
                if board[r][f].getPiece().isWhite() != self.isWhite:
                    ans.add(board[r][f])
                    break
            ans.add(board[r][f])
        
        r=self.rank
        f=self.file
        while(r>0 and f<7):
            r-=1
            f+=1
            if board[r][f].hasPiece():
                if board[r][f].getPiece().isWhite() != self.isWhite:
                    ans.add(board[r][f])
                break
            ans.add(board[r][f])
        
        r=self.rank
        f=self.file
        while(r < 7 and f > 0):
            r+=1
            f-=1
            if board[r][f].hasPiece():
                if board[r][f].getPiece().isWhite() != self.isWhite:
                    ans.add(board[r][f])
                break
            ans.add(board[r][f])
        
        r=self.rank
        f=self.file
        while(r < 7 and f < 7):
            r+=1
            f+=1
            if board[r][f].hasPiece():
                if board[r][f].getPiece().isWhite() != self.isWhite:
                    ans.add(board[r][f])
                break
            ans.add(board[r][f])
        
        return ans

class Knight(Piece):
    def __init__(self, isWhite, rank, file):
        super(isWhite, rank, file)
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = np.array([])
        for i in [-1,1]:
            for j in [-1,1]:
                try:
                    s = board[self.rank + 2*j][self.file+i]
                    if (not s.hasPiece() or s.getPiece().isWhite() != self.isWhite):
                        ans.add(s)
                try:
                    s = board[self.rank + j][self.file+2*i]
                    if (not s.hasPiece() or s.getPiece().isWhite() != self.isWhite):
                        ans.add(s)
        
        return ans

class Rook(Piece):
    def __init__(self, isWhite, rank, file):
        super(isWhite, rank, file)