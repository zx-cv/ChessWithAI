import numpy as np

class Piece(object):
    def __init__(self, isWhite, rank, file):
        self.isWhite = isWhite
        self.rank = rank
        self.file = file
        self.isPawn = False
    
    def getLegalMoves(self, board):
        pass

    def isPawn():
        return False
    
    def colorWhite(self):
        return self.isWhite
    
    def getRank(self):
        return self.rank
    
    def getFile(self):
        return self.file

class Pawn(Piece):
    def __init__(self, isWhite, rank, file):
        super(Pawn, self).__init__(isWhite, rank, file)
        self.firstMove = True
        self.secondMove = True
        self.isPawn = True
    
    def isPawn():
        return True
    
    def getLegalMoves(self, b):
        dir = 0
        if self.isWhite ^ b.isWhiteMove():
            dir = 1
        else:
            dir = -1
        
        board = b.getGrid()
        ans = np.array([])
        if self.firstMove:
            if (board[self.rank+dir][self.file] is None and board[self.rank+(2*dir)][self.file] is None):
                ans = np.append(ans, (self.rank+2*dir,self.file))
        if board[self.rank+dir][self.file] is None:
            ans = np.append(ans, (self.rank+dir,self.file))
        for f in [self.file-1,self.file+1]:
            if (f<0 or f>7):
                continue
            if (not (board[self.rank+dir][f] is None) and board[self.rank+dir][f].isWhite != self.isWhite):
                ans = np.append(ans, (self.rank+dir,f))
        return ans
    
    
    def moveTo(self, r, f):
        super(Pawn, self).moveTo(r, f)
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
        super(Bishop, self).__init__(isWhite, rank, file)
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = np.array([])

        r = self.rank
        f = self.file
        while (r>0 and f>0):
            r-=1
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                    break
            ans = np.append(ans, (r,f))
        
        r=self.rank
        f=self.file
        while(r>0 and f<7):
            r-=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f > 0):
            r+=1
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f < 7):
            r+=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        return ans

class Knight(Piece):
    def __init__(self, isWhite, rank, file):
        super(Knight, self).__init__(isWhite, rank, file)
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = np.array([])
        for i in [-1,1]:
            for j in [-1,1]:
                if (self.rank+2*j >= 0 and self.rank + 2*j < 8 and self.file +i >= 0 and self.file + i < 7):
                    s = board[self.rank + 2*j][self.file+i]
                    if (s is None or s.colorWhite() != self.isWhite):
                        ans = np.append(ans, (self.rank+2*j, self.file+i))
                if (self.rank+j >= 0 and self.rank + j < 8 and self.file +2*i >= 0 and self.file + 2*i < 8):
                    s = board[self.rank + j][self.file+2*i]
                    if (s is None or s.colorWhite() != self.isWhite):
                        ans = np.append(ans, (self.rank+j, self.file+2*i))
        
        return ans

class Rook(Piece):
    def __init__(self, isWhite, rank, file):
        super(Rook, self).__init__(isWhite, rank, file)
        self.moves = 0
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = np.array([])

        r = self.rank
        f = self.file
        while r>0:
            r-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r = self.rank
        f = self.file
        while f<7:
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r = self.rank
        f = self.file
        while r<7:
            r+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r = self.rank
        f = self.file
        while f>0:
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        return ans
        
        
    def moveTo(self, r, f):
        self.moves+=1
        self.rank = r
        self.file = f
        
    def moved(self):
        return self.moves > 1
        
    def subtractMove(self):
        self.moves -=1

class Queen(Piece):
    def __init__(self, isWhite, rank, file):
        super(Queen, self).__init__(isWhite, rank, file)
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = np.array([])

        r = self.rank
        f = self.file
        while (r>0 and f>0):
            r-=1
            f-=1
            if not (board[r][f] is None):
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                    break
            ans = np.append(ans, (r,f))
        
        r=self.rank
        f=self.file
        while(r>0 and f<7):
            r-=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f > 0):
            r+=1
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f < 7):
            r+=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r = self.rank
        f = self.file
        while r>0:
            r-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r = self.rank
        f = self.file
        while f<7:
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r = self.rank
        f = self.file
        while r<7:
            r+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))
        
        r = self.rank
        f = self.file
        while f>0:
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans = np.append(ans, (r,f))
                break
            ans = np.append(ans, (r,f))

        return ans

class King(Piece):
    whiteKingCheck = False
    blackKingCheck = False

    def __init__(self, isWhite, rank, file):
        super(King, self).__init__(isWhite, rank, file)
        self.moves = 0
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = np.array([])
        
        for i in [-1,0,1]:
            for j in [-1,0,1]:
                if ((i==0 and j==0) or self.rank+i<0 or self.rank+i>7 or self.file+j<0 or self.file+j>7):
                    continue
                s = board[self.rank + i][self.file + j]
                if (not s is None and s.colorWhite() == self.isWhite):
                    continue
                ans = np.append(ans, s)
        
        return ans
    
    def moveTo(self, r, f):
        self.moves+=1
        self.rank = r
        self.file = f
    
    def moved(self):
        return self.moves>1
    
    def substractMove(self):
        self.moves-=1

class Board:
    def __init__(self, boardstate):
        self.grid = np.empty((8,8), dtype=Piece)
        self.whitePieces = np.array([])
        self.blackPieces = np.array([])
        counter = 0
        for i in range(0,8):
            for j in range(0,8):
                if boardstate[counter] == '0':
                    self.grid[i][j] = None
                elif boardstate[counter] == 'P':
                    P = Pawn(True, i, j)
                    self.grid[i][j] = P
                    self.whitePieces = np.append(self.whitePieces, P)
                elif boardstate[counter] == 'p':
                    P = Pawn(False, i, j)
                    self.grid[i][j] = P
                    self.blackPieces = np.append(self.blackPieces, P)
                elif boardstate[counter] == 'B':
                    B = Bishop(True, i, j)
                    self.grid[i][j] = B
                    self.whitePieces = np.append(self.whitePieces, B)
                elif boardstate[counter] == 'b':
                    B = Bishop(False, i, j)
                    self.grid[i][j] = B
                    self.blackPieces = np.append(self.blackPieces, B)
                elif boardstate[counter] == 'N':
                    K = Knight(True, i, j)
                    self.grid[i][j] = K
                    self.whitePieces = np.append(self.whitePieces, K)
                elif boardstate[counter] == 'n':
                    K = Knight(False, i, j)
                    self.grid[i][j] = K
                    self.blackPieces = np.append(self.blackPieces, K)
                elif boardstate[counter] == 'R':
                    R = Rook(True, i, j)
                    self.grid[i][j] = R
                    self.whitePieces = np.append(self.whitePieces, R)
                elif boardstate[counter] == 'r':
                    R = Rook(False, i, j)
                    self.grid[i][j] = R
                    self.blackPieces = np.append(self.blackPieces, R)
                elif boardstate[counter] == 'Q':
                    Q = Queen(True, i, j)
                    self.grid[i][j] = Q
                    self.whitePieces = np.append(self.whitePieces, Q)
                elif boardstate[counter] == 'q':
                    Q = Queen(False, i, j)
                    self.grid[i][j] = Q
                    self.blackPieces = np.append(self.blackPieces, Q)
                elif boardstate[counter] == 'K':
                    K = King(True, i, j)
                    self.grid[i][j] = K
                    self.whitePieces = np.append(self.whitePieces, K)
                elif boardstate[counter] == 'k':
                    K = King(False, i, j)
                    self.grid[i][j] = K
                    self.blackPieces = np.append(self.blackPieces, K)
                counter+=1
        if boardstate[64] == '0':
            self.whiteMove = True
        else:
            self.whiteMove = False

    
    def getGrid(self):
        return self.grid
    
    def getWhitePieces(self):
        return self.whitePieces
    
    def getBlackPieces(self):
        return self.blackPieces
    
    def isWhiteMove(self):
        return self.whiteMove
    
    def makeMove(self, initialPiece, finalRank, finalFile):
        self.grid[initialPiece.getRank][initialPiece.getFile] = None
        pieceTaken = self.grid[finalRank][finalFile]
        if not (pieceTaken is None):
            if pieceTaken.isWhite:
                self.whitePieces = np.delete(self.whitePieces, pieceTaken)
            else:
                self.blackPieces = np.delete(self.blackPieces, pieceTaken)
        self.grid[finalRank][finalFile] = initialPiece
        return pieceTaken
        
    def unmakeMove(self, finalRank, finalFile, initialPiece, takenPiece):
        self.grid[finalRank][finalFile] = takenPiece
        if not (takenPiece is None):
            if takenPiece.isWhite:
                self.whitePieces = np.append(self.whitePieces, takenPiece)
            else:
                self.blackPieces = np.append(self.blackPieces, takenPiece)
        self.grid[initialPiece.getRank][initialPiece.getFile] = initialPiece
    
    def toString(self):
        grid = self.grid
        s = ''
        for row in grid:
            for square in row:
                if isinstance(square, Pawn):
                    if square.colorWhite():
                        s +='P'
                    else:
                        s += 'p'
                elif isinstance(square, Bishop):
                    if square.colorWhite():
                        s += 'B'
                    else:
                        s += 'b'
                elif isinstance(square, Knight):
                    if square.colorWhite():
                        s += 'N'
                    else:
                        s += 'n'
                elif isinstance(square, Rook):
                    if square.colorWhite():
                        s += 'R'
                    else:
                        s += 'r'
                elif isinstance(square, Queen):
                    if square.colorWhite():
                        s += 'Q'
                    else:
                        s += 'q'
                elif isinstance(square, King):
                    if square.colorWhite():
                        s += 'K'
                    else:
                        s += 'k'
                else:
                    s += '0'
        return s