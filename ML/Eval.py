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
        if self.board.isWhiteMove:
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
    
