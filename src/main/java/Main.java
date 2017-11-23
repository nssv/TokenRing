import static java.lang.Thread.sleep;

/**
 * Created by Stepan on 19.11.2017.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("123");

        TokenRing tokenRing = new TokenRing().createRing(7);


        try {
            tokenRing.start();

            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            tokenRing.interrupt();
        }


    }
}
