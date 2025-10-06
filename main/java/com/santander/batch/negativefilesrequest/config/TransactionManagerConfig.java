package com.santander.batch.negativefilesrequest.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * The type Transaction manager config.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.santander.batch.negativefilesrequest.repository")
public class TransactionManagerConfig {
    /**
     * Hikari config hikari config.
     *
     * @return the hikari config
     */
    @ConfigurationProperties(prefix = "ffnn.transactional-manager-jpa.datasource")
    @Bean
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    /**
     * Data source.
     *
     * @return the data source
     */
    @Bean
    @Qualifier("dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Jpa data source data source.
     *
     * @return the data source
     */
    @Bean
    @Qualifier("jpaDataSource")
    public DataSource jpaDataSource() {
        final HikariConfig hikariConfig = hikariConfig();
        final DataSource dataSource = DataSourceBuilder.create().build();
        hikariConfig.setPoolName("Hikari - Datasource JPA");
        final HikariDataSource hikariDs = (HikariDataSource) dataSource;
        hikariConfig.copyStateTo(hikariDs);
        return hikariDs;
    }

    /**
     * Emf local container entity manager factory bean.
     *
     * @param jpaDataSource the jpa data source
     * @return the local container entity manager factory bean
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean emf(@Qualifier("jpaDataSource") final DataSource jpaDataSource) {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setDataSource(jpaDataSource);
        entityManagerFactoryBean.setPackagesToScan("com.santander.batch.negativefilesrequest.persistence");
        entityManagerFactoryBean.setPersistenceUnitName("jpaFFNN");
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());
        //return entity manager
        return entityManagerFactoryBean;
    }

    /**
     * Additional properties properties.
     *
     * @return the properties
     */
    /*
     * View properties of org.hibernate.cfg.Environment
     */
    @ConfigurationProperties(prefix = "ffnn.transactional-manager-jpa.jpa.properties")
    @Bean
    Properties additionalProperties() {
        //return the properties
        return new Properties();
    }

    /**
     * Transaction manager jpa platform transaction manager.
     *
     * @param dataSource           the data source
     * @param entityManagerFactory the entity manager factory
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManagerJpa(@Qualifier("jpaDataSource") final DataSource dataSource,
                                                            EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setDataSource(dataSource);
        //return th transaction manager
        return jpaTransactionManager;
    }
}

