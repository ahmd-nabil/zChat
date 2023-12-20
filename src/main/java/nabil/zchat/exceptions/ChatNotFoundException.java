package nabil.zchat.exceptions;

/**
 * @author Ahmed Nabil
 */
public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException() {
        this( "Chat not found!");
    }

    public ChatNotFoundException(String message) {
        super(message);
    }

}
