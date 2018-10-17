package bettercare.wic.endpoint.support;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public abstract class JsonMarshaller<T> implements MessageBodyReader<T>, MessageBodyWriter<T>, ContextResolver<ObjectMapper> {

    private static List<MediaType> mediaTypes = Arrays.asList(MediaType.APPLICATION_JSON_TYPE);
    private static final ObjectMapper objectMapper = new ObjectMapperFactory().getObjectMapper();
    private static final DefaultExceptionMapper exceptionMapper = new DefaultExceptionMapper();

    /**
     * For @Consumes({WicOrderRepresentation})
     */
    @Override
    public boolean isReadable(Class<?> classOfObject, Type typeOfObject, Annotation[] annotations, MediaType mediaType) {
        return isTypeSupported(classOfObject, mediaType);
    }

    /**
     * For @Consumes({WicOrderRepresentation})
     * Called only if isReadable is True.
     */
    @Override
    public T readFrom(Class<T> classOfObject, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try {
            return objectMapper.readValue(entityStream, classOfObject);
        }
        catch (Exception ex) {
            Response response = exceptionMapper.toResponse(ex);
            throw new WebApplicationException(ex, response);
        }
    }


    /**
     * For @Produces({WicOrderRepresentation})
     */
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isTypeSupported(type, mediaType);
    }

    @Override
    public long getSize(T value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    /**
     * For @Produces({WicOrderRepresentation})
     * Called only if isWritable is True.
     */
    @Override
    public void writeTo(T entity, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        try {
            objectMapper.writeValue(entityStream, entity);
        }
        catch (Exception ex) {
            Response response = exceptionMapper.toResponse(ex);
            throw new WebApplicationException(ex, response);
        }
    }

    private boolean isTypeSupported(Class<?> classOfObject, MediaType mediaType) {
        for (MediaType supported : mediaTypes) {
            if (mediaType.isCompatible(supported)) {
                return true;
            }
        }
        return false;
    }

    /**
     * From ContextResolver
     * This is used internally by jersey ContextResolverFactory, javax.ws.rs.ext.Providers.
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }

}
