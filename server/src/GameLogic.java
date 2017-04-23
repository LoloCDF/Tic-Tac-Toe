
public class GameLogic
{
	// This attribute indicates if a playing slot is available
	private static Boolean playing = false;
	
	// Player names
	private static String pl1 = null;
	private static String pl2 = null;
	
	// Game grid
	private static String [][] grid =null;
	
	// Player turn
	private static int turn = 1;

	// Winner
	private static int winner = 0;

	// Last box checked
	private static int last_box = -1;

	// This method is invoked when a player starts a new game, we have to wait for another
	// and return the turn of the player
	public int newGame( String pl ) {
		StringBuilder sb;

		int turn = 1;
		
		if ( pl != null && pl1 == null ) {
			this.pl1 = pl;
			turn = 1;
			sb = new StringBuilder();
			sb.append("Player 1: \'");
			sb.append(pl);
			sb.append("\' has joined the game.");
			System.out.println(sb.toString());
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
			last_box = -1;
			sb = new StringBuilder();
			sb.append("Player 2: \'");
			sb.append(pl);
			sb.append("\' has joined the game.");
			System.out.println(sb.toString());
		}

		System.out.println("Starting the game...");

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

				// We save this box
				last_box = y*10+x;

				// We change the turn
				if (turn==1)
					turn=2;
				else
					turn=1;

				// Now we have to check for a winner
				if (isThisTheWinner(c, x, y)) {
					String nwinner = null;

					if(c.equals("x")) {
						winner = 1;
						nwinner = pl1;
					} else {
						winner = 2;
						nwinner = pl2;
					}

					StringBuilder sb = new StringBuilder();
					sb.append("Player ");
					sb.append(winner);
					sb.append(": \'");
					sb.append(nwinner);
					sb.append("\' has won the game.");
					System.out.println(sb);
					
					playing = false;

					resetGame();
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

	// This method provides information about which box has the oponent checked
	// Ex: 02 => y=0 x=2
	public int oponentBox() {
		return last_box;
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

	private void resetGame(){
		playing = false;
		pl1 = null;
		pl2 = null;
		grid =null;
		turn = 1;
		last_box = -1;
	}
}

