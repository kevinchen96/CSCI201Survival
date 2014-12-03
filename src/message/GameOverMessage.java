package message;

public class GameOverMessage extends Message{

	private String winner;
	
	public GameOverMessage(String winner) {
		super("GAMEOVER");
		this.winner = winner;
		// TODO Auto-generated constructor stub
	}
	
	public String getWinner(){
		return winner;
	}

}
