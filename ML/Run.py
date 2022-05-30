from Square import *
from Eval import *
from Search import *

with open('textfile.txt') as f:
    input = f.readline()
board = Board(input)
search = Search(board)
search.search(2, 0, 0)
thetuple = search.getBestMove()
with open('textfile.txt', 'w') as f:
    f.write(thetuple[0].getRank() + " " + thetuple[0].getFile() + " " + thetuple[1] + " " + thetuple[2])