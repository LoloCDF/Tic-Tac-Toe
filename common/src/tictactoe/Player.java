package tictactoe;

import java.io.Serializable;

public class Player implements Serializable {
    String nickname = null;
    int gamesPlayed = 0;
    int gamesWon = 0;
    Game [] games = null;

    public Player(){
    }

    public Player(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){ return this.nickname; }

    public int getGamesWon(){ return this.gamesWon; }

    public int getGamesPlayed(){ return this.gamesPlayed; }

    public Game[] getGames(){ return this.games; }

    public void setNickname(String nickname){ this.nickname = nickname; }

    public void setGamesWon(int gamesWon){ this.gamesWon = gamesWon; }

    public void setGamesPlayed(int gamesPlayed){ this.gamesPlayed = gamesPlayed; }

    public void setGames(Game [] games){ this.games = games; }

    public void addNewGame(Game game){ games[games.length] = game; }
}
