package bettercare.wic.service.config;

import bettercare.wic.dal.WicEntityManager;
import bettercare.wic.dal.WicTransactionManager;
import bettercare.wic.service.ObjectMapperFactory;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.service.TimeTrimmer;
import bettercare.wic.service.WicLogger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


@Configuration
public class ServiceConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return ObjectMapperFactory.instance();
  }

  @Bean
  public WicLogger wicLogger() {
    return new WicLogger();
  }

  @Bean
  public TimeTrimmer timeTrimmer() {
    return new TimeTrimmer();
  }

  @Bean
  public WicEntityManager wicEntityManager() {
    return new WicEntityManager();
  }

  @Bean
  public WicTransactionManager wicTransactionManager() {
    return new WicTransactionManager();
  }

  @Bean
  public SaveWicOrderService saveWicOrderService() {
    return new SaveWicOrderService();
  }

}
