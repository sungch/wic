package bettercare.wic.service.config;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.service.supports.FetchService;
//import bettercare.wic.service.marshaller.ObjectMapperFactory;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.service.supports.TimeTrimmer;
import bettercare.wic.dal.WicLogger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfiguration {

//  @Bean
//  public ObjectMapper objectMapper() {
//    return ObjectMapperFactory.instance();
//  }

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

  @Bean
  public FetchService fetchService() {
    return new FetchService();
  }
}
