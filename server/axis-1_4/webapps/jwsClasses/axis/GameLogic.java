package TicTacToeServer;

public class GameLogic
{
	// This attribute indicates if a playing slot is available
	private Boolean playing = false;
	
	// Player names
	private String pl1 = null;
	private String pl2 = null;
	
	// Game grid
	private String [][] grid =null;
	
	// Player turn
	private int turn = 1;

	// Winner
	private int winner = 0;

	// This method is invoked when a player starts a new game, we have to wait for another
	// and return the turn of the player
	public int newGame( String pl ) {
		int turn = 1;
		
		if ( pl != null && pl1 == null ) {
			this.pl1 = pl;
			turn = 1;
		} else if ( pl != null && pl2 == null ) {
			this.pl2 = pl;
			turn = 2;
			playing = true;
			grid = new String[][]{
				{"","",""},
				{"","",""},
				{"","",""}
			};
			winner = 0;
			this.turn = 1;
		}
		
		return turn;
	}
	
	// Here we inform to the player if the two slots have been completed so he can start to play
	public Boolean gameState(){
		return playing;
	}
	
	// This method can only be invoked by the right player, it returns t/f depending on the operation
	public void setBox(String c, int x, int y){

		if ( c.equals("x") || c.equals("o") && playing ) {
			// If the box is free, we type in the symbol
			if (grid[y][x].equals("")) {
				grid[y][x] = c;

				// Now we have to check for a winner
				if (isThisTheWinner(c, x, y)) {
					if(c.equals("x"))
						winner = 1;

					else
						winner = 2;

					playing = false;
				}
			}
		}
	}

	// After a setBox operation, this method must be invoked in order to check if there is a winner
	// return:
	// 0 -> no winner	1 -> Player 1 wins	2 -> Player 2 wins
	public int checkWinner (){
		return winner;
	}

	// This method provides information for the client about the turn
	public Boolean checkMyTurn(int turn){
		Boolean result = false;

		if(this.turn==turn)
			result = true;

		return result;
	}

	private Boolean isThisTheWinner(String c, int x, int y){
		Boolean result = false;

		if( y == 0 || x == 0 )
		{
			if((grid[0][0].equals(c)) &&
					(grid[1][1].equals(c)) &&
					(grid[2][2].equals(c)) ||
					(grid[2][2].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[0][0].equals(c)) ||
					(grid[0][0].equals(c)) &&
							(grid[1][0].equals(c)) &&
							(grid[2][0].equals(c)) ||
					(grid[2][0].equals(c)) &&
							(grid[1][0].equals(c)) &&
							(grid[0][0].equals(c)) ||
					(grid[0][0].equals(c)) &&
							(grid[0][1].equals(c)) &&
							(grid[0][2].equals(c)) ||
					(grid[0][2].equals(c)) &&
							(grid[0][1].equals(c)) &&
							(grid[0][0].equals(c)))
				result = true;
		}


		else if( y == 0 || x == 1 )
		{
			if((grid[0][0].equals(c)) &&
					(grid[0][1].equals(c)) &&
					(grid[0][2].equals(c)) ||
					(grid[0][2].equals(c)) &&
							(grid[0][1].equals(c)) &&
							(grid[0][0].equals(c)) ||
					(grid[0][1].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[2][1].equals(c)) ||
					(grid[2][1].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[0][1].equals(c)))
				result = true;
		}


		else if( y == 0 || x == 2 )
		{
			if((grid[0][0].equals(c)) &&
					(grid[0][1].equals(c)) &&
					(grid[0][2].equals(c)) ||
					(grid[0][2].equals(c)) &&
							(grid[0][1].equals(c)) &&
							(grid[0][0].equals(c)) ||
					(grid[0][2].equals(c)) &&
							(grid[1][2].equals(c)) &&
							(grid[2][2].equals(c)) ||
					(grid[2][2].equals(c)) &&
							(grid[1][2].equals(c)) &&
							(grid[0][2].equals(c)))
			result = true;
		}


		else if( y == 1 || x == 0 )
		{
			if((grid[1][0].equals(c)) &&
					(grid[1][1].equals(c)) &&
					(grid[1][2].equals(c)) ||
					(grid[1][2].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[1][0].equals(c)) ||
					(grid[0][0].equals(c)) &&
							(grid[1][0].equals(c)) &&
							(grid[2][0].equals(c)) ||
					(grid[2][0].equals(c)) &&
							(grid[1][0].equals(c)) &&
							(grid[0][0].equals(c)))
			result = true;
		}


		else if( y == 1 || x == 1 )
		{
			if((grid[0][0].equals(c)) &&
					(grid[1][1].equals(c)) &&
					(grid[2][2].equals(c)) ||
					(grid[2][2].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[0][0].equals(c)) ||
					(grid[1][0].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[1][2].equals(c)) ||
					(grid[1][2].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[1][0].equals(c)) ||
					(grid[0][2].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[2][0].equals(c)) ||
					(grid[2][0].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[0][2].equals(c)))
			result = true;
		}


		else if( y == 1 || x == 2 )
		{
			if((grid[1][2].equals(c)) &&
					(grid[1][1].equals(c)) &&
					(grid[2][0].equals(c)) ||
					(grid[1][2].equals(c)) &&
							(grid[2][0].equals(c)) &&
							(grid[1][1].equals(c)) ||
					(grid[0][2].equals(c)) &&
							(grid[1][2].equals(c)) &&
							(grid[2][2].equals(c)) ||
					(grid[2][2].equals(c)) &&
							(grid[1][2].equals(c)) &&
							(grid[0][2].equals(c)))
			result = true;
		}


		else if( y == 2 || x == 0 )
		{
			if((grid[2][0].equals(c)) &&
					(grid[2][1].equals(c)) &&
					(grid[2][2].equals(c)) ||
					(grid[2][0].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[0][2].equals(c)) ||
					(grid[2][0].equals(c)) &&
							(grid[1][0].equals(c)) &&
							(grid[0][0].equals(c)))
			result = true;
		}


		else if( y == 2 || x == 1 )
		{
			if((grid[2][1].equals(c)) &&
					(grid[1][1].equals(c)) &&
					(grid[0][1].equals(c)) ||
					(grid[0][1].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[2][1].equals(c)))
			result = true;
		}


		else if( y == 2 || x == 2 )
		{
			if((grid[0][0].equals(c)) &&
					(grid[1][1].equals(c)) &&
					(grid[2][2].equals(c)) ||
					(grid[2][2].equals(c)) &&
							(grid[1][1].equals(c)) &&
							(grid[0][0].equals(c)) ||
					(grid[2][0].equals(c)) &&
							(grid[2][1].equals(c)) &&
							(grid[2][2].equals(c)) ||
					(grid[2][2].equals(c)) &&
							(grid[2][1].equals(c)) &&
							(grid[2][0].equals(c)))
			result = true;
		}
		
		return result;
	}
}
