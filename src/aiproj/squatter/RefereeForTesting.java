package aiproj.squatter;

import java.util.Scanner;

import aiproj.squatter.JPF.JPFPlayer;

/*   
 *   Referee:
 *      A mediator between two players. It is responsible to initialize 
 *      the players and pass the plays between them and terminates the game. 
 *      It is the responsibility of the players to check whether they have won and
 *      maintain the board state.
 *
 *   @author lrashidi
 */


public class RefereeForTesting implements Piece{

	private static Player P1;
	private static Player P2;
	private static Move lastPlayedMove;
	
	/*
	 * Input arguments: first board size, second path of player1 and third path of player2
	 */
	public static void main(String[] args)
	{
		lastPlayedMove = new Move();
		int NumberofMoves = 0;
		int boardEmptyPieces=Integer.valueOf(6)*Integer.valueOf(6);
		System.out.println("Referee started !");
		try{
			P1 = new JPFPlayer();
			//P2 = (Player)(Class.forName(args[2]).newInstance());
		}
		catch(Exception e){
			System.out.println("Error "+ e.getMessage());
			System.exit(1);
		}
		
		P1.init(Integer.valueOf(6), WHITE);
		//P2.init(Integer.valueOf(args[0]), BLACK);
		
		
		while(boardEmptyPieces > 0 && P1.getWinner() == 0 /*&& P2.getWinner() ==0*/)
		{
			
			NumberofMoves++;
			lastPlayedMove=P1.makeMove();
			System.out.println("Placing to. "+lastPlayedMove.Row+":"+lastPlayedMove.Col+" by "+lastPlayedMove.P);		
			P1.printBoard(System.out);
			boardEmptyPieces--;			

			/*if(P2.opponentMove(lastPlayedMove)<0)
			{
				System.out.println("Exception: Player 2 rejected the move of player 1.");
				P1.printBoard(System.out);
				P2.printBoard(System.out);
				System.exit(1);
			}*/			
		if(P1.getWinner()==0){
				NumberofMoves++;	
				Move m = new Move();
				Scanner in = new Scanner(System.in);
				System.out.println("Enter Row:");
				m.Row = in.nextInt();
				System.out.println("Enter Column:");
				m.Col = in.nextInt();
				m.P = BLACK;
				lastPlayedMove = m;
				System.out.println("Placing to. "+lastPlayedMove.Row+":"+lastPlayedMove.Col+" by "+lastPlayedMove.P);
				boardEmptyPieces--;
				//P2.printBoard(System.out);
			
			if(P1.opponentMove(lastPlayedMove)<0)
			{
				System.out.println("Exception: Player 1 rejected the move of player 2.");
				P2.printBoard(System.out);
				P1.printBoard(System.out);
				System.exit(1);
			}
			}
			
		}
		System.out.println("--------------------------------------");
		//System.out.println("P2 Board is :");
		//P2.printBoard(System.out);
		System.out.println("P1 Board is :");
		P1.printBoard(System.out);
		
		System.out.println("Player one (White) indicate winner as: "+ P1.getWinner());
		System.out.println("Player two (Black) indicate winner as: "+ P2.getWinner());
		System.out.println("Total Number of Moves Played in the Game: "+ NumberofMoves);
		System.out.println("Referee Finished !");
	}
	

}
