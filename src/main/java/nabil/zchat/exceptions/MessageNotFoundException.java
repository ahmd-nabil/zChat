package nabil.zchat.exceptions;

/**
 * @author Ahmed Nabil
 */
public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException() {
        this( "Message not found!");
    }

    public MessageNotFoundException(String message) {
        super(message);
    }
}
