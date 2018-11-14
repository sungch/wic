package bettercare.wic.service;

public class InvalidVoucherException extends InvalidCustomerDataException {

    /**
     * serialver -classpath . bettercare.wic.service.InvalidVoucherException
     */
    private static final long serialVersionUID = 1261929128968811462L;

    public InvalidVoucherException() {
        super();
    }

    public InvalidVoucherException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVoucherException(String message) {
        super(message);
    }

    public InvalidVoucherException(Throwable cause) {
        super(cause);
    }
}
