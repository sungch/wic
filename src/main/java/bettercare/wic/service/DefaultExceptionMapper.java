package bettercare.wic.service;

import com.sun.jersey.api.ConflictException;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;

@Provider
@Component
@Scope("singleton")
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    @Resource private WicLogger wicLogger;

    @Override
    public Response toResponse(Exception ex) {

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        StringBuilder message = new StringBuilder(ex.getMessage());
        if(ex.getCause() != null) {
            message.append(ex.getCause().getMessage());
            message.append(Arrays.deepToString(ex.getStackTrace()));
        }

        if(ex instanceof ConflictException) {
            status = Response.Status.CONFLICT;
            message.append(ConflictException.class.getSimpleName());
        }

        if(ex instanceof OptimisticLockingFailureException) {
            status = Response.Status.CONFLICT;
            message.append(OptimisticLockingFailureException.class.getSimpleName());
        }

        if(ex instanceof WebApplicationException) {
            message.append(WebApplicationException.class.getSimpleName());
        }

        Response.ResponseBuilder responseBuilder = Response.status(status)
                .entity(message)
                .header("Content-Type", MediaType.TEXT_PLAIN_TYPE)
                .header("Content-Encoding", "identity");

        wicLogger.info("Response:" + responseBuilder.build().getEntity().toString(), this.getClass());

        return responseBuilder.build();
    }

}
