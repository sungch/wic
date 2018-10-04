package bettercare.wic.dal.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("bettercare.wic.dal")
@PropertySource({"classpath:/config.properties"})
public class RepositoryConfiguration {

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

    @Value("${jpa.pu.name}")
    private String jpaPuName;

    @Value("${jpa.entity.package}")
    private String jpaEntityPackage;

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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(basicDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setPersistenceUnitName(jpaPuName);
        factoryBean.setPackagesToScan(jpaEntityPackage);
        factoryBean.setJpaPropertyMap(jpaProperties());
        ;
        return factoryBean;
    }

    @Bean
    public JpaTransactionManager entityManager() throws SQLException {
        return new JpaTransactionManager(entityManagerFactory().getNativeEntityManagerFactory());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
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
    public Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());
        properties.put(NEW_GENERATOR_MAPPINGS, "true");
        properties.put(SHOW_SQL, "true");
        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        return hibernateJpaVendorAdapter;
    }

}
