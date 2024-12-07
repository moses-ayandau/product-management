//package com.moses.code.config;
//
//
//import jakarta.activation.DataSource;
//import jakarta.persistence.EntityManagerFactory;
//import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.moses.code.repository.jpa",
//        entityManagerFactoryRef = "postgresEntityManagerFactory",
//        transactionManagerRef = "postgresTransactionManager"
//)
//public class PostgresConfig {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource postgresDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("postgresDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.moses.code.entity.jpa") // Entities for PostgreSQL
//                .persistenceUnit("postgres")
//                .build();
//    }
//
//    @Bean
//    @Primary
//    public PlatformTransactionManager postgresTransactionManager(
//            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
