package tictactoe;

import java.util.Random;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.rpc.ParameterMode;

public class StartGame extends JFrame implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JButton box[];

	private String img = null;
	private String c = null;
	private String oimg = null;
	private String oc = null;

	private String host = null;

	private static final Random random = new Random();
	private static final String names[] =
	{
		" ", " ", " ", " ", " ",
		" ", " ", " ", " "
	};

	private boolean won = false;  // game is not won, set to false by default
	private int turn = 0;

	private Service service = null;
	private String endpoint = null;

	private Thread check = null;

	public StartGame(String IP, String nickname)
	{
		super("Proyecto Sistemas Distribuidos");
		StringBuilder sb = new StringBuilder();
		sb.append("Connecting to ");
		sb.append(nickname);
		sb.append("...");

		System.out.println(sb.toString());
		sb = new StringBuilder();
		sb.append("http://");
		sb.append(IP);
		sb.append(":8888/axis/services/GameLogic");
		this.endpoint = sb.toString();

		Container container;
		GridLayout layout;
		layout = new GridLayout(3, 3, 20, 20);
		container = getContentPane();
		setLayout(layout);

		box = new JButton[names.length];

		// adding all 8 box buttons to the grid layout
		for(int i = 0; i < names.length; i++)
		{
			box[i] = new JButton(names[i]);
			box[i].addActionListener(this);
			add(box[i]);
		}

		try {
			// Now we have to initialize the communication with the server
			service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName("newGame");
			call.addParameter(nickname, XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_INT);
			this.turn = (int) call.invoke(new Object[]{ nickname });

			if (this.turn==1) {
				this.c = "x";
				this.img = "x.png";
				this.oc = "o";
				this.oimg = "o.png";
			} else {
				this.c = "o";
				this.img = "o.png";
				this.oc = "x";
				this.oimg = "x.png";
			}
		} catch (Exception e) {
			System.out.println("Error in the communication with the server.");
		}

		System.out.println("Waiting for the other player...");
		while(!gameReady());

		System.out.println("The game has started.");
		if(this.turn == 2) {
			while (!checkMyTurn());
			refreshOponent();
		}

		System.out.println("It's my turn.");

		check = new Thread(new Checker());
		check.start();

		revalidate();
		repaint();
	}

	// this method (from lines 54 - 374 ) is used to determine which button the user clicked. by setting "x" or "o".
	// I plan to change this in the future to have the AI recognize if JButton was set to image/icon
	public void actionPerformed(ActionEvent event)
	{
		int x = 0;
		int y = 0;

		if (checkMyTurn()) {
			if (event.getSource() == box[0]) {
				box[0].setText(this.c);
				box[0].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[0].setEnabled(false);
				y = 0;
				x = 0;
			} else if (event.getSource() == box[1]) {
				box[1].setText(this.c);
				box[1].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[1].setEnabled(false);
				y = 0;
				x = 1;
			} else if (event.getSource() == box[2]) {
				box[2].setText(this.c);
				box[2].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[2].setEnabled(false);
				y = 0;
				x = 2;
			} else if (event.getSource() == box[3]) {
				box[3].setText(this.c);
				box[3].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[3].setEnabled(false);
				y = 1;
				x = 0;
			} else if (event.getSource() == box[4]) {
				box[4].setText(this.c);
				box[4].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[4].setEnabled(false);
				y = 1;
				x = 1;
			} else if (event.getSource() == box[5]) {
				box[5].setText(this.c);
				box[5].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[5].setEnabled(false);
				y = 1;
				x = 2;
			} else if (event.getSource() == box[6]) {
				box[6].setText(this.c);
				box[6].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[6].setEnabled(false);
				y = 2;
				x = 0;
			} else if (event.getSource() == box[7]) {
				box[7].setText(this.c);
				box[7].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[7].setEnabled(false);
				y = 2;
				x = 1;
			} else if (event.getSource() == box[8]) {
				box[8].setText(this.c);
				box[8].setIcon(new ImageIcon(getClass().getResource(this.img)));
				box[8].setEnabled(false);
				y = 2;
				x = 2;
			}

			sendBox(x, y);

			// We have to check if we are the winner
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
			}

			int winner = checkWinner();

			if (winner == this.turn)
				gameWonStatus(true);

			else if (winner != this.turn && winner != 0)
				gameWonStatus(false);
		}
	}


	// this method instantiate an object of the congratulations class
	// also, it alerts the user when they win, lose, or tie in-game
	public void gameWonStatus(boolean gameStatus)
	{
		this.won = gameStatus;

		if(gameStatus == true)
		{
			tictactoe.Congratulation application = new Congratulation();
			application.winningMessage();
			int reply = JOptionPane.showConfirmDialog(null, "Quit game?", "Do you wish to quit the game",
					JOptionPane.YES_NO_OPTION);

				if(reply == JOptionPane.YES_OPTION)
				{
					System.exit(0); // exits the entire program
				}
				else if(reply == JOptionPane.NO_OPTION)
				{
					dispose(); // closing current window
				}


		}

		else if(gameStatus == false)
		{
			Congratulation application = new Congratulation();
			application.losingMessage();

			dispose();
		}

		else if(gameStatus != false && gameStatus != true)
		{
			Congratulation application = new Congratulation();
			application.tiedMessage();

			JOptionPane.showMessageDialog(null, "unknown error");
			System.exit(0);
		}

	}

	private class Checker implements Runnable {
		@Override
		public void run() {
			while(true) {
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				}catch(Exception e){}
				refreshOponent();
			}
		}
	}

	private void refreshOponent(){
		// We have to update the last movement
		int oponent = oponentBox();

		if (oponent != -1) {
			int xo = oponent % 10;
			oponent = oponent / 10;
			int yo = oponent % 10;

			int nbox = yo*3 + xo;

			box[nbox].setText(this.oc);
			box[nbox].setIcon(new ImageIcon(getClass().getResource(this.oimg)));
			box[nbox].setEnabled(false);
		}


		int winner = checkWinner();

		if(winner==this.turn)
			gameWonStatus(true);

		else if(winner!=this.turn && winner!=0)
			gameWonStatus(false);
	}

	private Boolean gameReady() {
		Boolean game_started = false;
		try {
			// Now we have to initialize the communication with the server
			service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName("gameState");
			call.setReturnType(XMLType.XSD_BOOLEAN);
			 game_started = (Boolean) call.invoke(new Object[]{});
		} catch (Exception e) {
			System.out.println("Error in the communication with the server.");
		}

		return game_started;
	}

	private void sendBox(int x, int y) {

		try {
			// Now we have to initialize the communication with the server
			service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName("setBox");
			call.addParameter("c", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("x", XMLType.XSD_INT, ParameterMode.IN);
			call.addParameter("y", XMLType.XSD_INT, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_BOOLEAN);
			call.invoke(new Object[]{this.c, x, y});
		} catch (Exception e) {
			System.out.println("Error in the communication with the server.");
		}
	}

	private int oponentBox() {
		int result = 0;

		try {
			// Now we have to initialize the communication with the server
			service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName("oponentBox");
			call.addParameter("turn", XMLType.XSD_INT, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_INT);
			result = (int) call.invoke(new Object[]{ turn});
		} catch (Exception e) {
			System.out.println("Error in the communication with the server.");
		}

		return result;
	}

	private int checkWinner() {
		int winner = 0;

		try {
			// Now we have to initialize the communication with the server
			service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName("checkWinner");
			call.setReturnType(XMLType.XSD_INT);
			winner = (int) call.invoke(new Object[]{});
		} catch (Exception e) {
			System.out.println("Error in the communication with the server.");
		}

		return winner;
	}

	private Boolean checkMyTurn() {
		Boolean result = false;

		try {
			// Now we have to initialize the communication with the server
			service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName("checkMyTurn");
			call.addParameter("turn", XMLType.XSD_INT, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_BOOLEAN);
			result = (Boolean) call.invoke(new Object[]{this.turn});
		} catch (Exception e) {
			System.out.println("Error in the communication with the server.");
		}

		return result;
	}
}
