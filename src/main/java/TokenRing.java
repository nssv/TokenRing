import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.lang.Thread.sleep;

/**
 * Created by Stepan on 19.11.2017.
 */
public class TokenRing {

    private List<Node> listNodes = new ArrayList<>();
    private List<Message>[] listMessages;
    public static int length;

//    public TokenRing(RingConstructor ringConstructor){
//        this.ringConstructor = ringConstructor;
//    }

    public TokenRing(int length) {
        this.length= length;
    }

    public void createRing() {
//        TokenRing tokenRing = this;
        for (int i = 0; i < length; i++) {

            Node node = new Node(i);
            if ( i!= length-1) node.setNext(new Node(i + 1));
            else node.setNext(new Node(0));
//            new Node(i+1).start();}
            listNodes.add(node);

            Queue<Message> listMessage = new ConcurrentLinkedDeque<>();
            for (int j = 0; j < 1000; j++) {
                int rnd = new Random().nextInt(length);
                 if(rnd != i) listMessage.add(new Message(rnd, i, "1"));
            }
            node.setMessagesToSend(listMessage);
        }


        System.out.println("Token ring is generated");
        setFirstToken( 1);
        System.out.println("size"+listNodes.size());
//        return tokenRing;
    }

    public void start() {
        System.out.println( "number of nodes: " + listNodes.size());
        for (Node node : listNodes) {
            node.start();
        }
    }

    public void interrupt() {
        for (Node node : listNodes) {
            node.close();
        }
        Node.isInterrupted = true;
    }

    private void setFirstToken( int n){
        int rnd = new Random().nextInt(length);
        System.out.println("First token sent to: " + rnd);
        Token token = new Token(true, null);

        listNodes.get(n).sendMessage(token);
    }


//    public void sendMessage(Message message, int i) {
//        synchronized (listNodes) {
//            listMessages[i].add(message);
//        }
//    }


}
