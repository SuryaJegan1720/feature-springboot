package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class TransactionConfiguration {

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager(entityManagerFactory);

        // Enables JDBC savepoints for the NESTED demonstration.
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }
}
