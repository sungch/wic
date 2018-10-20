package bettercare.wic.service;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.IOException;
import java.time.Instant;

public class ObjectMapperFactory {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.registerModule(customSerializationModule());
    }

    private static SimpleModule customSerializationModule() {
        SimpleModule module = new SimpleModule("WicSerializationModule", new Version(1, 0, 0, null));
        module.addSerializer(Instant.class, new JsonInstantSerializer());
        module.addDeserializer(Instant.class, new JsonInstantDeserializer());
        return module;
    }

    public static ObjectMapper instance() {
        return objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}

class JsonInstantDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = jp.readValueAs(String.class);
        return Instant.parse(value);
    }
}

class JsonInstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(value.toString());
    }
}
