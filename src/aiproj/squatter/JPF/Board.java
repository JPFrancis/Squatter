//JOHN FRANCIS STUDENT ID: 732831 LOGIN: JOHNF1
package aiproj.squatter.JPF;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import aiproj.squatter.Move;


public class Board{

	private int boardDimension;
	private String boardValues[][];
	private int wScore = 0;
	private int bScore = 0;
	
	private HashMap<Integer, ArrayList<String>> whiteOutletMap;
	private HashMap<Integer, ArrayList<String>> blackOutletMap;
	
	public Board(Board board) {
		this.boardDimension = board.boardDimension;
		this.boardValues = new String[boardDimension][boardDimension];
		this.whiteOutletMap = new HashMap<Integer, ArrayList<String>>();
		this.blackOutletMap = new HashMap<Integer, ArrayList<String>>();
		
		for(int i=0; i<boardDimension; i++){
			for(int j=0; j<boardDimension; j++){
				
				this.boardValues[i][j] = board.boardValues[i][j];
				
				ArrayList<String> newEntry1 = new ArrayList<String>();
				for(String s : board.whiteOutletMap.get(i*boardDimension + j)){
					newEntry1.add(s);
				}
				whiteOutletMap.put(i*boardDimension + j, newEntry1);
				
				ArrayList<String> newEntry2 = new ArrayList<String>();
				for(String s : board.blackOutletMap.get(i*boardDimension + j)){
					newEntry2.add(s);
				}
				blackOutletMap.put(i*boardDimension + j, newEntry2);
			}
		}	
	}

	public Board(int d) {
		boardDimension = d;
		boardValues = new String[d][d];
		this.whiteOutletMap = new HashMap<Integer, ArrayList<String>>();
		this.blackOutletMap = new HashMap<Integer, ArrayList<String>>();
		
		for(int i=0; i<d; i++){
			for(int j=0; j<d; j++){
				boardValues[i][j]="+";
				ArrayList<String> newEntry1 = new ArrayList<String>();
				newEntry1.add("above");
				newEntry1.add("below");
				newEntry1.add("left");
				newEntry1.add("right");
				whiteOutletMap.put(i*boardDimension + j, newEntry1);
				ArrayList<String> newEntry2 = new ArrayList<String>();
				newEntry2.add("above");
				newEntry2.add("below");
				newEntry2.add("left");
				newEntry2.add("right");
				blackOutletMap.put(i*boardDimension + j, newEntry2);
				if(i==0||j==0||i==boardDimension-1||j==boardDimension-1){
					whiteOutletMap.get(i*boardDimension + j).clear();
					whiteOutletMap.get(i*boardDimension + j).add("border");
					blackOutletMap.get(i*boardDimension + j).clear();
					blackOutletMap.get(i*boardDimension + j).add("border");
				}
					
			}
		}
	}
	
	public void displayBoard(PrintStream output){
		for(int i = 0; i < boardDimension; i++){
			for(int j = 0; j < boardDimension; j++){
				output.print(boardValues[i][j] + " ");
			}
			output.println();
		}
	}
	
	public int isWinner(){

		for(int i=0; i<boardDimension; i++){
			for(int j=0; j<boardDimension; j++){
				if(boardValues[i][j].equals("+"))
					return 0;
				else if(boardValues[i][j].equals("-")||boardValues[i][j].equals("w")||boardValues[i][j].equals("b")){
					if(boardValues[i][j-1].equals("W")){
						if(!boardValues[i][j].equals("w"))
							wScore++;
						boardValues[i][j] = "W";
					}
					else if(boardValues[i][j-1].equals("B")){
						if(!boardValues[i][j].equals("b"))
							bScore++;
						boardValues[i][j] = "B";
					}	
				}
			}
		}
		
		if(wScore > bScore)
			return 1;
		else if(wScore < bScore)
			return 2;
		else
			return 3;
	}


	public boolean tryMove(String color, int row, int col) {
		
		if(row > -1 && row < boardDimension && col > -1 && col < boardDimension && color != null){
			if(boardValues[row][col].equals("+")){
				updateBoard(color, row, col);
				return true;
			}
		}
			
		return false;
			
	}

	private void updateBoard(String color, int row, int col) {
	
		boardValues[row][col] = color;
		
		blockOpponentNeighbors(color, row, col);
		for(int i=0; i<4; i++){
		updateBlocking(color);
		}
	}

	private void blockOpponentNeighbors(String color, int row, int col) {
		HashMap<Integer, ArrayList<String>> blockingMap = new HashMap<Integer, ArrayList<String>>();
		if(color == "W")
			blockingMap = whiteOutletMap;
		else
			blockingMap = blackOutletMap;
			
		if(row-1 >= 0){
			if(!boardValues[row-1][col].equals(color))
				blockingMap.get((row-1)*boardDimension + col).remove("below");
		}
		
		if(row+1<boardDimension){
			if(!boardValues[row+1][col].equals(color))
				blockingMap.get((row+1)*boardDimension + col).remove("above");
		}
		
		if(col-1 >= 0){
			if(!boardValues[row][col-1].equals(color))
				blockingMap.get(row*boardDimension + col - 1).remove("right");
		}
				
		if(col+1 < boardDimension){
			if(!boardValues[row][col+1].equals(color))
				blockingMap.get(row*boardDimension + col + 1).remove("left");
		}
	}
	
	private void updateBlocking(String color) {
		
		HashMap<Integer, ArrayList<String>> blockingMap = new HashMap<Integer, ArrayList<String>>();
		if(color == "W")
			blockingMap = whiteOutletMap;
		else
			blockingMap = blackOutletMap;
		
		for(int i=1; i<4; i++){
		for(Integer key : blockingMap.keySet()){
			if(blockingMap.get(key).size() == 0){
				blockOpponentNeighbors(color, key/boardDimension, key%boardDimension);
				relinquishBlock(color, key/boardDimension, key%boardDimension);
				if(boardValues[key/boardDimension][key%boardDimension] == "W")
					boardValues[key/boardDimension][key%boardDimension] = "w";
				if(boardValues[key/boardDimension][key%boardDimension] == "B")
					boardValues[key/boardDimension][key%boardDimension] = "b";
				if(boardValues[key/boardDimension][key%boardDimension] == "+")					
					boardValues[key/boardDimension][key%boardDimension] = "-";
			}
			if(blockingMap.get(key).size() == 1){
				String remainingOutlet = blockingMap.get(key).get(0);
				if(remainingOutlet == "below"){
					blockingMap.get(key+boardDimension).remove("above");
				}
				if(remainingOutlet == "above"){
					blockingMap.get(key-boardDimension).remove("below");
				}
				if(remainingOutlet == "left"){
					blockingMap.get(key-1).remove("right");
				}
				if(remainingOutlet == "right"){
					blockingMap.get(key+1).remove("left");
				}
			}
		}}
	}


	private void relinquishBlock(String color, int row, int col) {
		HashMap<Integer, ArrayList<String>> blockingMap = new HashMap<Integer, ArrayList<String>>();
		String opponentColor;
		if(color == "W"){
			blockingMap = blackOutletMap;
			opponentColor = "B";
		}
		else{
			blockingMap = whiteOutletMap;
			opponentColor = "W";
		}
		if(row-1 >= 0){
			if(boardValues[row-1][col].equals(opponentColor))
				blockingMap.get((row-1)*boardDimension + col).add("safe");
		}
		
		if(row+1<boardDimension){
			if(boardValues[row+1][col].equals(opponentColor))
				blockingMap.get((row+1)*boardDimension + col).add("safe");
		}
		
		if(col-1 >= 0){
			if(boardValues[row][col-1].equals(opponentColor))
				blockingMap.get(row*boardDimension + col - 1).add("safe");
		}
				
		if(col+1 < boardDimension){
			if(boardValues[row][col+1].equals(opponentColor))
				blockingMap.get(row*boardDimension + col + 1).add("safe");
		}
		
	}

	public ArrayList<Move> potentialMoves(int color) {
		ArrayList<Move> potentialMoves = new ArrayList<Move>();
		
		for(int i = 0; i<boardDimension; i++){
			for(int j = 0; j<boardDimension; j++){
				if(boardValues[i][j].equals("+")){
					Move m = new Move();
					m.P = color;
					m.Row = i;
					m.Col = j;
					potentialMoves.add(m);
				}
			}
		}
		return potentialMoves;
	}

	public int calculateScore(String color){
		HashMap<Integer, ArrayList<String>> blockingMap = new HashMap<Integer, ArrayList<String>>();
		
		if(color == "W")
			blockingMap = whiteOutletMap;
		else
			blockingMap = blackOutletMap;
		
		int score = 0;
		
		for(Integer key : blockingMap.keySet()){
			score += (blockingMap.get(key).size()); 
		}
		
		return score;
	}
}