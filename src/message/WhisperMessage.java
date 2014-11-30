package message;

public class WhisperMessage extends Message{
	private String message;
	private String[] to;
	private String from;
	private int i = 0;

	public WhisperMessage(String message, String[] to, String from) {
		super("WHISPER");
		this.message = message;
		this.to = to;
		this.from = from;
	}

	public String getMessage(){
		return message;
	}
	
	public String getFrom(){
		return from;
	}
	
	public String next(){
		try{
			int index = i;
			i++;
			return to[index];
		}catch(Exception e){
			return null;
		}
	}

}
