import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.sleep;

/**
 * Created by Stepan on 19.11.2017.
 */
public class TokenRing {

    private List<Node> listNodes = new ArrayList<>();
    private List<Message>[] listMessages;
    public static int length;

    public TokenRing(int length) {
        this.length= length;
    }

    public void createRing() {
        for (int i = 0; i < length; i++) {
            Node node = new Node(i);
            listNodes.add(node);
            ConcurrentLinkedQueue<Message> listMessage = new ConcurrentLinkedQueue<>();
            for (int j = 0; j < 1000000; j++) {
                int rnd = new Random().nextInt(length);
                 if(rnd != i) listMessage.add(new Message(rnd, i, "1"));
            }
            node.setMessagesToSend(listMessage);

        }

        for (int i = 0; i < listNodes.size(); i++) {
             if(i != listNodes.size()-1) listNodes.get(i).setNext(listNodes.get(i+1));
             else listNodes.get(i).setNext(listNodes.get(0));
        }

        System.out.println("Token ring is generated");
        setFirstToken( 1);
        System.out.println("size"+listNodes.size());
    }

    public void start() {
        System.out.println( "number of nodes: " + listNodes.size());
        for (Node node : listNodes) {
            node.start();
        }
    }

    public void interrupt() throws FileNotFoundException, UnsupportedEncodingException {
        for (Node node : listNodes) {
            node.close();
        }
        Node.isInterrupted = true;

        for (int i = 0; i < listNodes.size(); i++) {
            writeToCSV(listNodes.get(i).getReceivedMessage(), i);
        }

    }

    private void setFirstToken( int n){
        int rnd = new Random().nextInt(length);
        System.out.println("First token sent to: " + rnd);
        Token token = new Token(true, null);

        listNodes.get(n).sendMessage(token);
    }


    private static final String CSV_SEPARATOR = ";";
    private static void writeToCSV(List<Message> productList, int i) throws FileNotFoundException, UnsupportedEncodingException {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("products"+i+".csv" ), "UTF-8"));
            for (Message product : productList)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append((product.getEndTime() - product.getStartTime()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append((product.getEndTime() - product.getStartTime())/60);

                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e){}
    }

}
