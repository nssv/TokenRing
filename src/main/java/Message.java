/**
 * Created by Stepan on 20.11.2017.
 */
public class Message {

    private final int destination;
    private final int sender;
    private final String messageBody;

    public Message(int destination, int sender, String messageBody) {
        this.destination = destination;
        this.sender = sender;
        this.messageBody = messageBody;
    }

    public int getDestination() {
        return destination;
    }

    public int getSender() {
        return sender;
    }

    public String getMessageBody() {
        return messageBody;
    }


//    private final String message;
//    private final boolean isToken;
//
//    public Message(String message, boolean isToken) {
//        this.message= message;
//        this.isToken = isToken;
//    }

}
