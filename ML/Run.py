class Search:
    def __init__(self, board):
        self.board = board
        self.bestMove = (None, 0, 0)
        self.maxdepth = 4
    
    def search(self, depth, alpha, beta):
        if depth == 0:
            return Evaluation(self.board).evaluate()
        if self.board.isWhiteMove():
            pieces = self.board.getWhitePieces()
        else:
            pieces = self.board.getBlackPieces()
        moves = []
        for piece in pieces:
            moves.append(piece.getLegalMoves(self.board))
        if len(moves) == 0:
            return self.searchAllCaptures(self, alpha, beta)
        
        for piece in pieces:
            moves = piece.getLegalMoves(self.board)
            for move in moves:
                secondpiece = self.board.getGrid()[move[0]][move[1]]
                if not (secondpiece is None):
                    if secondpiece.colorWhite() == piece.colorWhite():
                        continue
                pieceTaken = self.board.makeMove(piece, move[0], move[1])
                evaluation = -1*self.search(depth-1, -1*beta, -1*alpha)
                self.board.unmakeMove(move[0], move[1], piece, pieceTaken)
                if depth == 3:
                    print (str(piece) + str(move[0]) + str(move[1]))
                if evaluation >= beta:
                    return beta
                if evaluation > alpha:
                    if depth == 3:
                        self.bestMove = (piece, move[0], move[1])
                        print("bestMove: " + str(piece) + str(move[0]) + " " + str(move[1]))
                    alpha = evaluation
        return alpha
    
    def searchAllCaptures(self, alpha, beta):
        evaluation = Evaluation(self.board).evaluate()
        if evaluation >= beta:
            return beta
        if evaluation > alpha:
            alpha = evaluation
        pieces = self.board.getWhitePieces() + self.board.getBlackPieces()
        for piece in pieces:
            moves = piece.getLegalMoves(self.board)
            for move in moves:
                secondpiece = self.board.getGrid()[move[0]][move[1]]
                if not (secondpiece is None):
                    if secondpiece.colorWhite() == piece.colorWhite():
                        continue
                pieceTaken = self.board.makeMove(piece, move[0], move[1])
                if pieceTaken is None:
                    self.board.unmakeMove(move[0], move[1], piece, pieceTaken)
                    continue
                evaluation = -1*self.searchAllCaptures(-1*beta, -1*alpha)
                self.board.unmakeMove(move[0], move[1], piece, pieceTaken)
                if depth == 3:
                    print (str(piece) + str(move[0]) + str(move[1]))
                if evaluation >= beta:
                    return beta
                if evaluation > alpha:
                    if depth == 3:
                        print("bestMove: " + str(piece) + str(move[0]) + " " + str(move[1]))
                        self.bestMove = (piece, move[0], move[1])
                    alpha = evaluation
        return alpha
                
    def getBestMove(self):
        return self.bestMove



class Evaluation:
    def __init__(self, board):
        self.board = board
        self.pawnValue = 100
        self.bishopValue = 300
        self.knightValue = 300
        self.rookValue = 500
        self.queenValue = 900
        self.kingValue = float('inf')
        self.endgameMaterialStart = self.rookValue*2+self.bishopValue+self.knightValue
        #table datas taken from SebLague at https://github.com/SebLague and https://www.youtube.com/sebastianlague
        self.pawnTable = [
            [0,  0,  0,  0,  0,  0,  0,  0],
			[50, 50, 50, 50, 50, 50, 50, 50],
			[10, 10, 20, 30, 30, 20, 10, 10],
			[5,  5, 10, 25, 25, 10,  5,  5],
			[0,  0,  0, 20, 20,  0,  0,  0],
			[5, -5,-10,  0,  0,-10, -5,  5],
			[5, 10, 10,-20,-20, 10, 10,  5],
			[0,  0,  0,  0,  0,  0,  0,  0]
        ]
        self.knightTable = [
            [-50,-40,-30,-30,-30,-30,-40,-50],
            [-40,-20,  0,  0,  0,  0,-20,-40],
            [-30,  0, 10, 15, 15, 10,  0,-30],
            [-30,  5, 15, 20, 20, 15,  5,-30],
            [-30,  0, 15, 20, 20, 15,  0,-30],
            [-30,  5, 10, 15, 15, 10,  5,-30],
            [-40,-20,  0,  5,  5,  0,-20,-40],
            [-50,-40,-30,-30,-30,-30,-40,-50]
        ]
        self.bishopTable = [
            [-20,-10,-10,-10,-10,-10,-10,-20],
			[-10,  0,  0,  0,  0,  0,  0,-10],
			[-10,  0,  5, 10, 10,  5,  0,-10],
			[-10,  5,  5, 10, 10,  5,  5,-10],
			[-10,  0, 10, 10, 10, 10,  0,-10],
			[-10, 10, 10, 10, 10, 10, 10,-10],
			[-10,  5,  0,  0,  0,  0,  5,-10],
			[-20,-10,-10,-10,-10,-10,-10,-20]
        ]
        self.rookTable = [
            [0,  0,  0,  0,  0,  0,  0,  0],
			[5, 10, 10, 10, 10, 10, 10,  5],
			[-5,  0,  0,  0,  0,  0,  0, -5],
			[-5,  0,  0,  0,  0,  0,  0, -5],
			[-5,  0,  0,  0,  0,  0,  0, -5],
			[-5,  0,  0,  0,  0,  0,  0, -5],
			[-5,  0,  0,  0,  0,  0,  0, -5],
			[0,  0,  0,  5,  5,  0,  0,  0]
        ]
        self.queenTable = [
            [-20,-10,-10, -5, -5,-10,-10,-20],
			[-10,  0,  0,  0,  0,  0,  0,-10],
			[-10,  0,  5,  5,  5,  5,  0,-10],
			[-5,  0,  5,  5,  5,  5,  0, -5],
			[0,  0,  5,  5,  5,  5,  0, -5],
			[-10,  5,  5,  5,  5,  5,  0,-10],
			[-10,  0,  5,  0,  0,  0,  0,-10],
			[-20,-10,-10, -5, -5,-10,-10,-20]
        ]

    
    def evaluate(self):
        whiteEval = self.countMaterial(True)
        whitePieces = self.board.getWhitePieces()
        for piece in whitePieces:
            if isinstance(piece, Pawn):
                whiteEval += self.pawnTable[piece.getRank()][piece.getFile()]
            elif isinstance(piece, Knight):
                whiteEval += self.knightTable[piece.getRank()][piece.getFile()]
            elif isinstance(piece, Bishop):
                whiteEval += self.bishopTable[piece.getRank()][piece.getFile()]
            elif isinstance(piece, Rook):
                whiteEval += self.rookTable[piece.getRank()][piece.getFile()]
            elif isinstance(piece, Queen):
                whiteEval += self.queenTable[piece.getRank()][piece.getFile()]
        blackEval = self.countMaterial(False)
        blackPieces = self.board.getBlackPieces()
        for piece in blackPieces:
            if isinstance(piece, Pawn):
                blackEval += self.pawnTable[7-piece.getRank()][7-piece.getFile()]
            elif isinstance(piece, Knight):
                blackEval += self.knightTable[7-piece.getRank()][7-piece.getFile()]
            elif isinstance(piece, Bishop):
                blackEval += self.bishopTable[7-piece.getRank()][7-piece.getFile()]
            elif isinstance(piece, Rook):
                blackEval += self.rookTable[7-piece.getRank()][7-piece.getFile()]
            elif isinstance(piece, Queen):
                blackEval += self.queenTable[7-piece.getRank()][7-piece.getFile()]

        evaluation = whiteEval - blackEval
        perspective = -1
        if self.board.isWhiteMove():
            perspective = 1
        return evaluation * perspective

    
    def EndgamePhaseWeight(self, materialCountWithoutPawns):
        if materialCountWithoutPawns > self.endgameMaterialStart:
            return 0
        else:
            return 1-(materialCountWithoutPawns/self.endgameMaterialStart)
        
    def countMaterial(self, colorBoolean):
        material = 0
        pieces = []
        if colorBoolean:
            pieces = self.board.getWhitePieces()
        else:
            pieces = self.board.getBlackPieces()
        for piece in pieces:
            if isinstance(piece, Pawn):
                material += self.pawnValue
            elif isinstance(piece, Bishop):
                material += self.bishopValue
            elif isinstance(piece, Knight):
                material += self.knightValue
            elif isinstance(piece, Rook):
                material += self.rookValue
            elif isinstance(piece, Queen):
                material += self.queenValue
        return material


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
        if not self.isWhite:
            dir = 1
        else:
            dir = -1
        
        board = b.getGrid()
        ans = []
        if self.firstMove:
            if (board[self.rank+dir][self.file] is None and board[self.rank+(2*dir)][self.file] is None):
                ans.append((self.rank+2*dir,self.file))
        if board[self.rank+dir][self.file] is None:
            ans.append((self.rank+dir,self.file))
        for f in [self.file-1,self.file+1]:
            if (f<0 or f>7):
                continue
            if (not (board[self.rank+dir][f] is None) and board[self.rank+dir][f].colorWhite() != self.isWhite):
                ans.append((self.rank+dir,f))
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
        ans = []

        r = self.rank
        f = self.file
        while (r>0 and f>0):
            r-=1
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                    break
            ans.append((r,f))
        
        r=self.rank
        f=self.file
        while(r>0 and f<7):
            r-=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f > 0):
            r+=1
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f < 7):
            r+=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        return ans

class Knight(Piece):
    def __init__(self, isWhite, rank, file):
        super(Knight, self).__init__(isWhite, rank, file)
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = []
        for i in [-1,1]:
            for j in [-1,1]:
                if (self.rank+2*j >= 0 and self.rank + 2*j < 8 and self.file +i >= 0 and self.file + i < 7):
                    s = board[self.rank + 2*j][self.file+i]
                    if (s is None or s.colorWhite() != self.isWhite):
                        ans.append((self.rank+2*j, self.file+i))
                if (self.rank+j >= 0 and self.rank + j < 8 and self.file +2*i >= 0 and self.file + 2*i < 8):
                    s = board[self.rank + j][self.file+2*i]
                    if (s is None or (s.colorWhite() is not self.isWhite)):
                        ans.append((self.rank+j, self.file+2*i))
        
        return ans

class Rook(Piece):
    def __init__(self, isWhite, rank, file):
        super(Rook, self).__init__(isWhite, rank, file)
        self.moves = 0
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = []

        r = self.rank
        f = self.file
        while r>0:
            r-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r = self.rank
        f = self.file
        while f<7:
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r = self.rank
        f = self.file
        while r<7:
            r+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r = self.rank
        f = self.file
        while f>0:
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
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
        ans = []

        r = self.rank
        f = self.file
        while (r>0 and f>0):
            r-=1
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                    break
            ans.append((r,f))
        
        r=self.rank
        f=self.file
        while(r>0 and f<7):
            r-=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f > 0):
            r+=1
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r=self.rank
        f=self.file
        while(r < 7 and f < 7):
            r+=1
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r = self.rank
        f = self.file
        while r>0:
            r-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r = self.rank
        f = self.file
        while f<7:
            f+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r = self.rank
        f = self.file
        while r<7:
            r+=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        
        r = self.rank
        f = self.file
        while f>0:
            f-=1
            if not board[r][f] is None:
                if board[r][f].colorWhite() != self.isWhite:
                    ans.append((r,f))
                break
            ans.append((r,f))
        

        return ans

class King(Piece):
    whiteKingCheck = False
    blackKingCheck = False

    def __init__(self, isWhite, rank, file):
        super(King, self).__init__(isWhite, rank, file)
        self.moves = 0
    
    def getLegalMoves(self, b):
        board = b.getGrid()
        ans = []
        
        for i in [-1,0,1]:
            for j in [-1,0,1]:
                if ((i==0 and j==0) or self.rank+i<0 or self.rank+i>7 or self.file+j<0 or self.file+j>7):
                    continue
                s = board[self.rank + i][self.file + j]
                if (s is None):
                    ans.append((self.rank+i, self.file+j))
                    continue
                if (s.colorWhite() == self.isWhite):
                    continue
                ans.append((self.rank+i, self.file+j))
        
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
        self.grid = [
            [None, None, None, None, None, None, None, None],
            [None, None, None, None, None, None, None, None],
            [None, None, None, None, None, None, None, None],
            [None, None, None, None, None, None, None, None],
            [None, None, None, None, None, None, None, None],
            [None, None, None, None, None, None, None, None],
            [None, None, None, None, None, None, None, None],
            [None, None, None, None, None, None, None, None]
        ]
        self.whitePieces = []
        self.blackPieces = []
        counter = 0
        for i in range(0,8):
            for j in range(0,8):
                if boardstate[counter] == '0':
                    self.grid[i][j] = None
                elif boardstate[counter] == 'P':
                    P = Pawn(True, i, j)
                    self.grid[i][j] = P
                    self.whitePieces.append(P)
                elif boardstate[counter] == 'p':
                    P = Pawn(False, i, j)
                    self.grid[i][j] = P
                    self.blackPieces.append(P)
                elif boardstate[counter] == 'B':
                    B = Bishop(True, i, j)
                    self.grid[i][j] = B
                    self.whitePieces.append(B)
                elif boardstate[counter] == 'b':
                    B = Bishop(False, i, j)
                    self.grid[i][j] = B
                    self.blackPieces.append(B)
                elif boardstate[counter] == 'N':
                    K = Knight(True, i, j)
                    self.grid[i][j] = K
                    self.whitePieces.append(K)
                elif boardstate[counter] == 'n':
                    K = Knight(False, i, j)
                    self.grid[i][j] = K
                    self.blackPieces.append(K)
                elif boardstate[counter] == 'R':
                    R = Rook(True, i, j)
                    self.grid[i][j] = R
                    self.whitePieces.append(R)
                elif boardstate[counter] == 'r':
                    R = Rook(False, i, j)
                    self.grid[i][j] = R
                    self.blackPieces.append(R)
                elif boardstate[counter] == 'Q':
                    Q = Queen(True, i, j)
                    self.grid[i][j] = Q
                    self.whitePieces.append(Q)
                elif boardstate[counter] == 'q':
                    Q = Queen(False, i, j)
                    self.grid[i][j] = Q
                    self.blackPieces.append(Q)
                elif boardstate[counter] == 'K':
                    K = King(True, i, j)
                    self.grid[i][j] = K
                    self.whitePieces.append(K)
                elif boardstate[counter] == 'k':
                    K = King(False, i, j)
                    self.grid[i][j] = K
                    self.blackPieces.append(K)
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
        self.grid[initialPiece.getRank()][initialPiece.getFile()] = None
        pieceTaken = self.grid[finalRank][finalFile]
        if not (pieceTaken is None):
            if pieceTaken.colorWhite():
                if pieceTaken in self.whitePieces:
                    self.whitePieces.remove(pieceTaken)
            else:
                if pieceTaken in self.blackPieces:
                    self.blackPieces.remove(pieceTaken)
        self.grid[finalRank][finalFile] = initialPiece
        self.whiteMove = not self.whiteMove
        return pieceTaken
        
    def unmakeMove(self, finalRank, finalFile, initialPiece, takenPiece):
        self.grid[finalRank][finalFile] = takenPiece
        if not (takenPiece is None):
            if takenPiece.colorWhite():
                self.whitePieces.append(takenPiece)
            else:
                self.blackPieces.append(takenPiece)
        self.whiteMove = not self.whiteMove
        self.grid[initialPiece.getRank()][initialPiece.getFile()] = initialPiece


with open('textfile.txt') as f:
    input = f.readline()
board = Board(input)
print(str(board.isWhiteMove()))
search = Search(board)
search.search(3, -10000, 100000)
thetuple = search.getBestMove()
with open('textfile.txt', 'w') as f:
    f.write(str(thetuple[0].getRank()) + " " + str(thetuple[0].getFile()) + " " + str(thetuple[1]) + " " + str(thetuple[2]))