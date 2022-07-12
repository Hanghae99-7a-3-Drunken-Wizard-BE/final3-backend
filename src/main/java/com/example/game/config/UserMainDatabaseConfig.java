package com.example.game.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
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
//        entityManagerFactoryRef = "mainEntityManager",
//        transactionManagerRef = "mainTransactionManager",
//        basePackages = "com.example.game.repository.user"   // 회원 Repository 패키지 경로
//)
//public class UserMainDatabaseConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean mainEntityManager(){
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(mainDataSource());
//
//        // Entity 패키지 경로
//        em.setPackagesToScan("com.example.game.model.user");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        // Hibernate 설정
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
//        em.setJpaPropertyMap(properties);
//        return em;
//    }
//
//    @Bean
//    @Primary
//    public DataSource mainDataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(env.getProperty("spring.main.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.main.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.main.datasource.password"));
//        return dataSource;
//    }
//
//    @Bean
//    @Primary
//    public PlatformTransactionManager mainTransactionManager(){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(mainEntityManager().getObject());
//        return transactionManager;
//    }
//}
