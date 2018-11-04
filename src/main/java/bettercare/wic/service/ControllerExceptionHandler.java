package bettercare.wic.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
  ResponseEntity<String> badRequestHandler(ValidationException ve) {
    return new ResponseEntity<>(ve.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
