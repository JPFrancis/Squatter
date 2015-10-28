//JOHN FRANCIS STUDENT ID: 732831 LOGIN: JOHNF1
package aiproj.squatter.JPF;

import java.io.PrintStream;
import java.util.ArrayList;

import aiproj.squatter.*;

public class JPFPlayer implements Player, Piece {

	public int playerColor;
	private Board currentBoard;
	
	@Override
	public int init(int n, int p) {
		
		if((n != 6 && n != 7) || (p != WHITE && p != BLACK))
			return -1;
		else{
			playerColor = p;
			
			currentBoard = new Board(n);
		}
			
		return 0;
	}

	@Override
	public Move makeMove() {
		String opponentColor = null;
		int opponentColorPiece = 0;
		String myColor = null;
		if(playerColor == WHITE){
			myColor = "W";
			opponentColor = "B";
			opponentColorPiece = BLACK;
		}
		else if(playerColor == BLACK){
			myColor = "B";
			opponentColor = "W";
			opponentColorPiece = WHITE;
		}
		
		ArrayList<Move> potentialMoves = new ArrayList<Move>();
		potentialMoves = currentBoard.potentialMoves(playerColor); 
		
		
		Move bestMove = null;
		int bestScore = 100000;
		
		
		for(Move m : potentialMoves){
			Board temp = new Board(currentBoard);
			temp.tryMove(myColor, m.Row, m.Col);
			int score1 = temp.calculateScore(myColor);
			int score2 = 0;
			int totalScore = 10000000;
			ArrayList<Move> secondMoves = new ArrayList<Move>();
			secondMoves = temp.potentialMoves(opponentColorPiece);
			if(secondMoves.isEmpty())
				totalScore = score1;
			else{
				int bestScore2 = 100000;
				for(Move m2 : secondMoves){
					Board temp2 = new Board(temp);
					temp2.tryMove(opponentColor, m2.Row, m2.Col);
					score2 = temp2.calculateScore(opponentColor);
					if(score2 < bestScore2){
						bestScore2 = score2;
					}
				}
				totalScore = score1-bestScore2;
			}
			if(totalScore < bestScore){
				bestScore = totalScore;
				bestMove = m; 
			}
		}
			
		currentBoard.tryMove(myColor, bestMove.Row, bestMove.Col);
			
		return bestMove;
	}

	@Override
	public int opponentMove(Move m) {
		
		String piece = null;
		if(m.P == WHITE)
			piece = "W";
		else if(m.P == BLACK)
			piece = "B";

		
		if(currentBoard.tryMove(piece, m.Row, m.Col)){
			return 1;
		}
		else{
			return -1;
		}
		
	}

	@Override
	public int getWinner() {
		
		Board temp = new Board(currentBoard);
		new Board(currentBoard);
			return temp.isWinner();
	}

	@Override
	public void printBoard(PrintStream output) {
		
		currentBoard.displayBoard(output);

	}
}
