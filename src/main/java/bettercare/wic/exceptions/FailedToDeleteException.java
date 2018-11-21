package bettercare.wic.exceptions;


public class FailedToDeleteException extends Exception {

    /**
     * serialver -classpath . bettercare.wic.exceptions.InvalidVoucherException
     */
    private static final long serialVersionUID = 1261929128968811462L;

    public FailedToDeleteException() {
        super();
    }

    public FailedToDeleteException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FailedToDeleteException(String message) {
        super(message);
    }

    public FailedToDeleteException(Throwable throwable) {
        super(throwable);
    }
}
