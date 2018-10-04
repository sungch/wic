package bettercare.wic.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:/config.properties"})
public class JpaConfig {

    @Value("${db.url:}")
    private String dbUrl;

    @Value("${db.username:}")
    private String dbUsername;

    @Value("${db.password:}")
    private String dbPassword;

    @Value("${db.pool.init.size:5}")
    private int dbPoolInitSize;

    @Value("${db.pool.max.size:20}")
    private int dbPoolMaxSize;

    @Value("${jpa.pu.name:wicpu}")
    private String jpaPuName;

    @Value("${jpa.model.package}")
    private String jpaModelPackage;

    @Value("${jdbc.driver:com.mysql.jdbc.Driver}")
    private String jdbcDriver;

    @Value("${validation.query:select 1}")
    private String validationQuery;

    @Bean
    public BasicDataSource basicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setInitialSize(dbPoolInitSize);
        dataSource.setMaxTotal(dbPoolMaxSize);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(basicDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaDialect(hibernateJpaDialect());
        factoryBean.setPersistenceUnitName(jpaPuName);
        factoryBean.setPackagesToScan(jpaModelPackage);
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory());
        jpaTransactionManager.setJpaDialect(hibernateJpaDialect());
        return jpaTransactionManager;
    }

    @Bean
    public HibernateJpaDialect hibernateJpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(transactionManager());
    }

    private final static String NEW_GENERATOR_MAPPINGS = "hibernate.id.new_generator_mappings";

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(NEW_GENERATOR_MAPPINGS, "true");
        properties.setProperty(SHOW_SQL, "true");
        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

}
