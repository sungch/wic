package bettercare.wic.exceptions;

import bettercare.wic.utils.WicLogger;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.jersey.api.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@ControllerAdvice
@ResponseBody
public class DefaultExceptionMapper {

    private int statusCode;
    private String message;
    @Resource WicLogger wicLogger;

    @ExceptionHandler({Exception.class})
    public Response toResponse(Exception ex) {

        setExceptionContent(ex);
        setStatusCode(ex);
        return buildResponse().build();
    }

    private Response.ResponseBuilder buildResponse() {

        Response.ResponseBuilder builder = Response.status(this.statusCode)
                .entity(this.message)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_ENCODING, "identity");
        wicLogger.error("Exception Response:" + builder.toString(), ExceptionMapper.class);
        return builder;
    }

    private void setExceptionContent(Exception ex) {

        this.message = ex.getMessage() == null ? "" : ex.getMessage();
        if(ex.getCause() != null) {
            this.message += ex.getCause().getMessage();
        }
    }

    private void setStatusCode(Exception ex) {

        if(ex instanceof NotFoundException || ex instanceof UsernameNotFoundException || ex instanceof EntityNotFoundException) {
            this.statusCode = Response.Status.NOT_FOUND.getStatusCode();
        }

        else if(ex instanceof ValidationException
                || ex instanceof MethodArgumentNotValidException
                || ex instanceof DataIntegrityViolationException
                || ex instanceof JsonParseException
                || ex instanceof JsonMappingException) {
            this.statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        }

        else if(ex instanceof WebApplicationException) {
            WebApplicationException webappEx = (WebApplicationException) ex;
            this.statusCode = webappEx.getResponse().getStatus();
        }

        else if(ex instanceof InvalidVoucherException || ex instanceof InvalidCustomerDataException) {
            this.statusCode = Response.Status.BAD_REQUEST.getStatusCode();

        }

        else if(ex instanceof FailedToDeleteException) {
            this.statusCode = Response.Status.NOT_FOUND.getStatusCode();
        }

        else if(ex instanceof org.springframework.security.access.AccessDeniedException) {
            this.statusCode = Response.Status.UNAUTHORIZED.getStatusCode();
        }

        else {
            this.statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        }
    }
}
