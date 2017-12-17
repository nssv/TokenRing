import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Stepan on 19.11.2017.
 */
public class Node {

    private ConcurrentLinkedQueue<Message> messagesToSend;
    static volatile boolean isInterrupted = false;
    private final int id;
    private volatile ConcurrentLinkedQueue<Token> tokens;
    private Node nextNode;
    private List<Message> receivedMessage ;
    private ConcurrentLinkedQueue<Message> prevTokens;
    private ConcurrentLinkedQueue<Message> nextTokens;

    private Thread threadNode;


    Node(int id) {
        this.tokens = new ConcurrentLinkedQueue<>();
        this.id = id;
        this.receivedMessage = new ArrayList<>();
//        this.nextNode = nextNode;

        this.threadNode = new Thread(() -> {
            while (!isInterrupted) {
                Token tok = tokens.poll();
                if (tok != null) {
                    if (tok.isToken()) {
                        Message message = getMessagesToSend().poll();
                        message.setStartTime(System.nanoTime());
                        tok.setMessage(message);
                        tok.setToken(false);
                        sendToNext(tok);
                    } else {
//                        System.out.println("RECEIVED" + tok.getMessage().getMessageBody());
                        Message mes = tok.getMessage();
                        if (mes.getDestination() == id) {
                            receivedMessage.add(mes);
                            sendToNext(tok);
                        } else {
                            if (mes.getSender() == id) {
                                tok.setToken(true);
                                sendToNext(tok);
                                mes.setEndTime(System.nanoTime());
                            }
                            sendToNext(tok);
                        }
                    }
                }
            }
        });
    }

    public void start() {
        System.out.println("thread for node: " + id + "is started");
        threadNode.start();
    }

    public void close() {
        System.out.println("thread for node " + id + " is interrupted");
        System.out.println("node " + id + "received messages "+ receivedMessage.size());

        threadNode.interrupt();
    }

    public void sendToNext(Token token) {
            synchronized (nextNode.tokens){
                nextNode.tokens.add(token);
            }
    }

    public Queue<Message> getMessagesToSend() {
        return messagesToSend;
    }

    public void setMessagesToSend(ConcurrentLinkedQueue<Message> messagesToSend) {
        this.messagesToSend = messagesToSend;
    }

    private void receive(Token token) {
        tokens.add(token);
    }

    void setNext(Node node) {
        System.out.println("Next node of" + id + " is  " + node.id);
        this.nextNode = node;
    }

    public void sendMessage(Token token) {
        synchronized (tokens){
            tokens.add(token);
            System.out.println("TOKEN " + token + " in node " + id);
        }
    }

    public List<Message> getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(List<Message> receivedMessage) {
        this.receivedMessage = receivedMessage;
    }
}
