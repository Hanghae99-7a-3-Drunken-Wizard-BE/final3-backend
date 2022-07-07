package com.example.game.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@PropertySource({"classpath:application-database.properties"})
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "subEntityManager",
//        transactionManagerRef = "subTransactionManager",
//        basePackages = "com.example.game.Game.repository"   // 게임 Repository 패키지 경로
//)
//public class GameSubDatabaseConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean subEntityManager(){
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(subDataSource());
//        em.setPackagesToScan("com.example.game.Game");  // Game 패키지를 전체를 넣음 과연 될런지 :안됨
//
//        HibernateJpaVendorAdapter vendorAdapter =new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
//        em.setJpaPropertyMap(properties);
//        return em;
//    }
//
//    @Bean
//    public DataSource subDataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(env.getProperty("spring.sub.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.sub.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.sub.datasource.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager subTransactionManager(){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(subEntityManager().getObject());
//        return transactionManager;
//    }
//}
