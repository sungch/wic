package bettercare.wic.service;

public class FieldErrorMessage {

  private String field;
  private String message;

  /**
   * This is only for @ExceptionHandler(MethodArgumentNotValidException.class)
   */
  public FieldErrorMessage(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
