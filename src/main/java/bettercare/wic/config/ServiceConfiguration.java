package bettercare.wic.config;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.marshaller.DefaultExceptionMapper;
import bettercare.wic.service.marshaller.JasonMarshallerForWicOrderRepresentation;
import bettercare.wic.service.marshaller.ObjectMapperFactory;
import bettercare.wic.service.marshaller.WicMediaType;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.service.supports.TimeTrimmer;
import bettercare.wic.dal.WicLogger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


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

  @Bean
  public EntityService fetchService() {
    return new EntityService();
  }

  @Bean
  @Scope("singleton")
  public JasonMarshallerForWicOrderRepresentation wicJsonMarshaller() {
    return new JasonMarshallerForWicOrderRepresentation();
  }

  @Bean
  public WicMediaType wicMediaType() {
    return new WicMediaType();
  }

  @Bean
  @Scope("singleton")
  public DefaultExceptionMapper defaultExceptionMapper() {
    return new DefaultExceptionMapper();
  }

  @Bean
  public WicOrderRepresentation wicOrderRepresentation() {
    return new WicOrderRepresentation();
  }
}
