/**
 * Created by Stepan on 19.11.2017.
 */

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        TokenRing tokenRing = new TokenRing(10);
        tokenRing.createRing();

        try {
            tokenRing.start();
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            tokenRing.interrupt();
        }
    }
}
