import numpy as np
from Eval import *
from Square import *

class Search:
    def __init__(self, board):
        self.board = board
        self.bestMove = None
    
    def search(self, depth, alpha, beta):
        if depth == 0:
            return Evaluation(self.board).evaluate()
        
        pieces = np.concatenate((self.board.getWhitePieces(), self.board.getBlackPieces()))
        moves = np.array([])
        for piece in pieces:
            moves = np.concatenate((moves, piece.getLegalMoves(self.board)))
        if moves.size == 0:
            return self.searchAllCaptures(self, alpha, beta)
        
        for piece in pieces:
            moves = piece.getLegalMoves(self.board)
            for move in moves:
                print(move)
                pieceTaken = self.board.makeMove(piece, move[0], move[1])
                evaluation = -1*Search(depth-1, -1*beta, -1*alpha)
                self.board.unmakeMove(move[0], move[1], piece, pieceTaken)
                if evaluation >= beta:
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
        pieces = np.concatenate((self.board.getWhitePieces(), self.board.getBlackPieces()))
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
                    self.bestMove = (piece, move[0], move[1])
                    alpha = evaluation
        return alpha
                
    def getBestMove(self):
        return self.bestMove