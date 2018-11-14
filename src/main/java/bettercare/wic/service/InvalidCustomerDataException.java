package bettercare.wic.service;

public class InvalidCustomerDataException extends Exception {

    /**
     * Use built-in tool serialver:
     * serialver -classpath . bettercare.wic.service.InvalidCustomerDataException
     */

    private static final long serialVersionUID = 4317558271068897047L;

    public InvalidCustomerDataException() {
        super();
    }

    public InvalidCustomerDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCustomerDataException(String message) {
        super(message);
    }

    public InvalidCustomerDataException(Throwable cause) {
        super(cause);
    }
}
