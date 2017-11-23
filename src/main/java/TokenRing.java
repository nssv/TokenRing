import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stepan on 19.11.2017.
 */
public class TokenRing {

    private List<Node> listNodes = new ArrayList<Node>();
    private List<Message>[] listMessages;

    public TokenRing createRing(int nodes) {
        TokenRing tokenRing = new TokenRing();
        for (int i = 0; i < nodes; i++) {
            listNodes.add(new Node(i));
        }

        return tokenRing;
    }

    public void start() {
        for (Node node : listNodes) {
            node.start();
        }
    }

    public void interrupt() {
        for (Node node : listNodes) {
            node.interrupt();
        }
    }

//
//    private void setFirstToken(Message message, int n){
//        listNodes.get(n).sendMessage();
//    }

    public void sendMessage(Message message, int i){
        synchronized (listNodes){
            listMessages[i].add(message);
        }

    }


}
