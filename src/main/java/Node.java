import java.util.List;

/**
 * Created by Stepan on 19.11.2017.
 */
public class Node extends Thread {

    private List<Message> messagesToSend;

    private final int id;
    public  static volatile boolean isInterrupted = false;


    public Node(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while(!isInterrupted){
            System.out.println("1");
//            if(!token.isEmpty && token.getAddress){
//                token.message.getAddres
//            } else{
//                sendToNext();
//            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public synchronized void start() {
        super.start();
    }


    public void sendToNext() {

    }

    public List<Message> getMessagesToSend() {
        return messagesToSend;
    }

    public void setMessagesToSend(List<Message> messagesToSend) {
        this.messagesToSend = messagesToSend;
    }

}
