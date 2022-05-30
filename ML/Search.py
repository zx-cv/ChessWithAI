class Search:
    def __init__(self, board):
        self.board = board
        self.bestMove = (None, 0, 0)
    
    def search(self, depth, alpha, beta):
        if depth == 0:
            return Evaluation(self.board).evaluate()
        
        pieces = self.board.getWhitePieces().append(self.board.getBlackPieces())
        moves = []
        for piece in pieces:
            moves.append(piece.getLegalMoves(self.board))
        if len(moves) == 0:
            return self.searchAllCaptures(self, alpha, beta)
        
        for piece in pieces:
            moves = piece.getLegalMoves(self.board)
            for move in moves:
                pieceTaken = self.board.makeMove(piece, move[0], move[1])
                evaluation = -1*self.search(depth-1, -1*beta, -1*alpha)
                self.board.unmakeMove(move[0], move[1], piece, pieceTaken)
                if evaluation >= beta:
                    self.bestMove = (piece, move[0], move[1])
                    return beta
                if evaluation > alpha:
                    self.bestMove = (piece, move[0], move[1])
                    alpha = evaluation
        return alpha
    
    def searchAllCaptures(self, alpha, beta):
        evaluation = Evaluation(self.board).evaluate()
        if evaluation >= beta:
            return beta
        if evaluation > alpha:
            alpha = evaluation
        pieces = self.board.getWhitePieces().append(self.board.getBlackPieces())
        for piece in pieces:
            moves = piece.getLegalMoves(self.board)
            for move in moves:
                pieceTaken = self.board.makeMove(piece, move[0], move[1])
                if pieceTaken is None:
                    self.board.unmakeMove(move[0], move[1], piece, pieceTaken)
                    continue
                evaluation = -1*self.searchAllCaptures(-1*beta, -1*alpha)
                self.board.unmakeMove(move[0], move[1], piece, pieceTaken)
                if evaluation >= beta:
                    return beta
                if evaluation > alpha:
                    alpha = evaluation
        return alpha
                
    def getBestMove(self):
        return self.bestMove