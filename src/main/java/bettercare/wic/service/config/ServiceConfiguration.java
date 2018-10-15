package bettercare.wic.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public  WicLogger wicLogger() {
    return new WicLogger();
  }

  @Bean
  public TimeTrimmer timeTrimmer() {
    return new TimeTrimmer();
  }

}
