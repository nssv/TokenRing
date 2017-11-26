/**
 * Created by Stepan on 19.11.2017.
 */

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {

        TokenRing tokenRing = new TokenRing(3);
        tokenRing.createRing();

        try {
            tokenRing.start();
            sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            tokenRing.interrupt();
        }
    }
}
