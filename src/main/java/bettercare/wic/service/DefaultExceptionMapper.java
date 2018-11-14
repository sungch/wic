package bettercare.wic.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.jersey.api.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ControllerAdvice
@ResponseBody
public class DefaultExceptionMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionMapper.class);

    private int statusCode;
    private String message;

    @ExceptionHandler({Exception.class})
    public Response toResponse(Exception ex) {

        this.statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        this.message = ex.getMessage();

        if(ex.getCause() != null) {
            this.message += ex.getCause().getMessage();
        }

        setStatusCode(ex);

        Response.ResponseBuilder builder = Response.status(this.statusCode)
                .entity(this.message)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_ENCODING, "identity");

        LOGGER.warn("Responded as an error with body:" + this.message);

        return builder.build();
    }

    private void setStatusCode(Exception ex) {
        if(ex instanceof NotFoundException) {
            this.statusCode = Response.Status.NOT_FOUND.getStatusCode();
        }

        if(ex instanceof ValidationException
                || ex instanceof MethodArgumentNotValidException
                || ex instanceof DataIntegrityViolationException
                || ex instanceof JsonParseException
                || ex instanceof JsonMappingException) {
            this.statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        }

        if(ex instanceof WebApplicationException) {
            WebApplicationException webappEx = (WebApplicationException) ex;
            this.statusCode = webappEx.getResponse().getStatus();
        }

        if(ex instanceof InvalidVoucherException || ex instanceof InvalidCustomerDataException) {
            this.statusCode = Response.Status.BAD_REQUEST.getStatusCode();

        }
    }
}