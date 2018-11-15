package bettercare.wic.exceptions;

public class InvalidProductDataException extends Exception {

    /**
     * Use built-in tool serialver:
     * serialver -classpath . bettercare.wic.exceptions.InvalidProductDataException
     */
    private static final long serialVersionUID = -5783574163579475561L;

    public InvalidProductDataException() {
        super();
    }

    public InvalidProductDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProductDataException(String message) {
        super(message);
    }

    public InvalidProductDataException(Throwable cause) {
        super(cause);
    }
}
