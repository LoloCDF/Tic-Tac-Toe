package tictactoe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameLogic
{
	// This attribute indicates if a playing slot is available
	private static Boolean playing = false;
	
	// Game grid
	private static String [][] grid =null;
	
	// Player turn
	private static int turn = 1;

	// Winner
	private static int winner = 0;

	// Last box checked
	private static int last_box = -1;

	// Players historic
	private ArrayList<Player> players = null;

	// Loggin
	private static final Logger log = Logger.getLogger("GameLogic");

	// These are the players of the game
	private String pl1 = null;
	private String pl2 = null;

	public GameLogic(){
		players = new ArrayList<Player>();
		FileHandler fh;

		try {
			fh = new FileHandler("TicTacToe.log");
			log.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			log.info("STARTING TIC-TAC-TOE SERVER");

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// This method is invoked when a player starts a new game, we have to wait for another
	// and return the turn of the player
	public int newGame( String pl ) {
		StringBuilder sb;
		int turn = 1;

		if ( pl != null && pl1 == null ) {
			login(pl);
			this.pl1 = pl;
			turn = 1;
			log.info("Player 1: \"" + pl + "\" has joined the game.");
		} else if ( pl != null && pl2 == null ) {
			login(pl);
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
			log.info("Player 2: \"" + pl + "\" has joined the game.");
			log.info("Starting the game...");
		}

		return turn;
	}
	
	// Here we inform to the player if the two slots have been completed so he can start to play
	public Boolean gameState(){
		return playing;
	}

	// Here the client can get its profile
	public Player getMyProfile(String nickname){ return getPlayer(nickname); }

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

					getPlayer(nwinner).setGamesWon(getPlayer(nwinner).getGamesWon()+1);

					Game game = new Game(pl1, pl2, nwinner, new Date());
					getPlayer(pl1).addNewGame(game);
					getPlayer(pl2).addNewGame(game);
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
	public int oponentBox(int turn) {
		int result = -1;
		if(this.turn!=turn)
			result = last_box;

		return result;
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

	// A user has logged in so, we have to get it data
	// If it's a new user we have to create a new player
	private void login(String nickname){
		// We have to find if the player has logged in previously
		Player player = getPlayer(nickname);

		if(player != null){
			player.setGamesPlayed(player.getGamesPlayed()+1);
		} else {
			player = new Player(nickname);
			players.add(player);
		}
	}

	// Get Player
	private Player getPlayer(String nickname){
		// We have to find if the player has logged in previously
		Player player = null;
		for(Player aux : players){
			if(aux.getNickname().equals(nickname))
				player = aux;
		}

		return player;
	}
}

