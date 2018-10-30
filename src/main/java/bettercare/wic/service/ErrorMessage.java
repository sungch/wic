package bettercare.wic.service;

/**
 * This is used only by
 * @ControllerAdvice
 * public class ControllerExceptionHandler
 */
public class ErrorMessage {

  private String status;
  private String message;
  private String field;

  public ErrorMessage(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
