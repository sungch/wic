package bettercare.wic.config;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.utils.WicTimeUtils;
import bettercare.wic.utils.WicLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfiguration {

  @Bean
  public WicLogger wicLogger() {
    return new WicLogger();
  }

  @Bean
  public WicTimeUtils wicTimeUtils() {
    return new WicTimeUtils();
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
  public EntityService fetchService() {
    return new EntityService();
  }

  @Bean
  public WicOrderRepresentation wicOrderRepresentation() {
    return new WicOrderRepresentation();
  }
}
