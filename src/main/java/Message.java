/**
 * Created by Stepan on 20.11.2017.
 */
public class Message {

    private final int destination;
    private final int sender;
    private final String messageBody;
    private long startTime;
    private long endTime;

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

    public void setStartTime(long startTime){
        this.startTime = startTime;
    }

    public void setEndTime(long endTime){
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

}
