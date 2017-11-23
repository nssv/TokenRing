/**
 * Created by Stepan on 20.11.2017.
 */
public class Token {

    private Message message;
    private boolean isToken;

    private Token(boolean isToken, Message message) {
        this.isToken = isToken;
        this.message = message;
    }

    public void setToken(boolean token) {
        this.isToken = token;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isToken() {
        return isToken;
    }

    public Message getMessage() {
        return message;
    }


}
