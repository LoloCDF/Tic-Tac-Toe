package tictactoe;

import java.io.Serializable;
import java.util.Date;

public class Game implements Serializable {
    
    // Players who played
    String pl1 = null;
    String pl2 = null;
    
    // Winner
    String winner = null;
    
    // Date
    Date date = null;
    
    public Game(){
    }
    
    public Game(String pl1, String pl2, String winner, Date date){
        this.pl1 = pl1;
        this.pl2 = pl2;
        this.winner = winner;
        this.date = date;
    }
    
    public String getPl1() { return this.pl1; }
    
    public String getPl2() { return this.pl2; }
    
    public String getWinner() { return this.winner; }
    
    public Date getDate() { return this.date; }
    
    public void setPl1(String pl1){ this.pl1 = pl1; }
    
    public void setPl2(String pl2){ this.pl2 = pl2; }
    
    public void setWinner(String winner){ this.winner = winner; }
    
    public void setDate(Date date){ this.date = date; }   
}
