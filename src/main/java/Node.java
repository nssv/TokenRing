import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.sleep;

/**
 * Created by Stepan on 19.11.2017.
 */
public class Node {

    private Queue<Message> messagesToSend;
    static volatile boolean isInterrupted = false;
    private final int id;
    private Queue<Token> tokens;
    private Node nextNode;
    private List<Message> receivedMessage;

    private Thread threadNode;


    Node(int id) {
        this.tokens = new ConcurrentLinkedQueue<>();
        this.id = id;
        this.nextNode = nextNode;

        this.threadNode = new Thread(() -> {

            while(!isInterrupted){
                Token tok = tokens.poll();

                if (tok != null){
                    System.out.println("token get"+tok);
                    if(tok.isToken()){
                        tok.setMessage(messagesToSend.poll());
                        tok.setToken(false);
                        sendToNext(tok);
                        System.out.println("token - yes");
                    } else {
                        System.out.println("RECEIVED" + tok.getMessage().getMessageBody());
                        Message mes = tok.getMessage();
                        if(mes.getDestination() == id){
                            System.out.println("received by id:" + id);
                            receivedMessage.add(mes);
                        } else {
                            if (mes.getSender() == id){
                                tok.setToken(true);
                                sendToNext(tok);
                                System.out.println("received by sender");
                            }
                            sendToNext(tok);
                        }
                    }
                }
            }

            List a = receivedMessage;
            System.out.println("TRY TO CLOSE");
        });
    }

    public void start(){
        System.out.println("thread for node: "+ id + "is started");
        threadNode.start();
    }

    public void close(){
        System.out.println("thread for node " + id + " is interrupted");
        threadNode.interrupt();
    }

    public synchronized void sendToNext(Token token) {
            nextNode.tokens.add(token);
    }

    public Queue<Message> getMessagesToSend() {
        return messagesToSend;
    }

    public void setMessagesToSend(Queue<Message> messagesToSend) {
        this.messagesToSend = messagesToSend;
    }

    private void receive(Token token){
        tokens.add(token);
    }

    void setNext(Node node) {
        System.out.println("Next node of" + id + " is  "+ node.id);
        this.nextNode = node;
    }

    public void sendMessage(Token token) {
//        this.nextNode.receive(token);
        tokens.add(token);
    }

}
