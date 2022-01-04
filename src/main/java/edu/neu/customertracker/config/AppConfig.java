package edu.neu.customertracker.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("edu.neu.customertracker")
@PropertySource({"classpath:persistence-mysql.properties"})
public class AppConfig implements WebMvcConfigurer {

  @Autowired
  private Environment env;

  private final Logger logger = Logger.getLogger(getClass().getName());

  // define a bean for ViewResolver
  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/view/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  @Bean
  public DataSource dataSource() {
    // create connection pool
    ComboPooledDataSource dataSource = new ComboPooledDataSource();

    // set jdbc driver
    try {
      dataSource.setDriverClass(env.getProperty("jdbc.driver"));
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }

    // for sanity's sake, let's log url and user ... just to make sure we are reading the data
    logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
    logger.info("jdbc.user=" + env.getProperty("jdbc.user"));

    // set database connection props
    dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
    dataSource.setUser(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.password"));

    // set connection pool props
    dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
    dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
    dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
    dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

    return dataSource;
  }

  // need a helper method
  // read environment property and convert to int
  private int getIntProperty(String propName) {
    return Integer.parseInt(Objects.requireNonNull(env.getProperty(propName)));
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    // create session factory
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

    // set properties
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
    sessionFactory.setHibernateProperties(getHibernateProperties());
    return sessionFactory;
  }

  private Properties getHibernateProperties() {
    // set hibernate properties
    Properties props = new Properties();
    props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
    props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    return props;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    // setup transaction manager based on session factory
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory);
    return transactionManager;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
  }
}
