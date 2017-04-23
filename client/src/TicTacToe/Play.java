package TicTacToe;

import javax.swing.JFrame;


public class Play
{
	public static void main(String args[])
	{

		if(args.length!=2)
			System.out.println("USE: TicTacToe [SERVER_IP] [NICKNAME]");

		else {
			String IP = args[0];
			String nickname = args[1];

			StartMenu playGame = new StartMenu(IP, nickname);


			playGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
}
