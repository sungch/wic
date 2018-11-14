package bettercare.wic.config;

import bettercare.wic.dal.WicLogger;
import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.service.TimeTrimmer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * /actuator/*
 * /actuator/health
 * /actuator/info
 * https://www.baeldung.com/spring-boot-actuators
 *
 * /auditevents – lists security audit-related events such as user login/logout. Also, we can filter by principal or type among others fields
 * /beans – returns all available beans in our BeanFactory. Unlike /auditevents, it doesn’t support filtering
 * /conditions – formerly known as /autoconfig, builds a report of conditions around auto-configuration
 * /configprops – allows us to fetch all @ConfigurationProperties beans
 * /env – returns the current environment properties. Additionally, we can retrieve single properties
 * /flyway – provides details about our Flyway database migrations
 * /health – summarises the health status of our application
 * /heapdump – builds and returns a heap dump from the JVM used by our application
 * /info – returns general information. It might be custom data, build information or details about the latest commit
 * /liquibase – behaves like /flyway but for Liquibase
 * /logfile – returns ordinary application logs
 * /loggers – enables us to query and modify the logging level of our application
 * /metrics – details metrics of our application. This might include generic metrics as well as custom ones
 * /prometheus – returns metrics like the previous one, but formatted to work with a Prometheus server
 * /scheduledtasks – provides details about every scheduled task within our application
 * /sessions – lists HTTP sessions given we are using Spring Session
 * /shutdown – performs a graceful shutdown of the application
 * /threaddump – dumps the thread information of the underlying JVM
 *
 *
 * @Component
 * public class DownstreamServiceHealthIndicator implements ReactiveHealthIndicator {
 *
 *     @Override
 *     public Mono<Health> health() {
 *         return checkDownstreamServiceHealth().onErrorResume(
 *           ex -> Mono.just(new Health.Builder().down(ex).build())
 *         );
 *     }
 *
 *     private Mono<Health> checkDownstreamServiceHealth() {
 *         // we could use WebClient to check health reactively
 *         return Mono.just(new Health.Builder().up().build());
 *     }
 * }
 */
@Configuration
public class SecurityConfiguration {

//  @Bean
//  public SecurityWebFilterChain securityWebFilterChain(
//          ServerHttpSecurity http) {
//    return http.authorizeExchange()
//            .pathMatchers("/actuator/**").permitAll()
//            .anyExchange().authenticated()
//            .and().build();
//  }


}
