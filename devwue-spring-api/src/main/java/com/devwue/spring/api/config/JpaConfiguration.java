package com.devwue.spring.api.config;

import com.devwue.spring.api.model.ModelsMarker;
import com.devwue.spring.api.repository.RepositoriesMarker;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackageClasses = {RepositoriesMarker.class},
        transactionManagerRef = "apiTransactionManager",
        entityManagerFactoryRef = "apiEntityManagerFactory"
)
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class, ModelsMarker.class})
public class JpaConfiguration {
    private final HibernateProperties hibernateProperties;
    private final JpaProperties jpaProperties;

    public JpaConfiguration(HibernateProperties hibernateProperties, JpaProperties jpaProperties) {
        this.hibernateProperties = hibernateProperties;
        this.jpaProperties = jpaProperties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean apiEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                                          DataSource ApiDataSource) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        return builder.dataSource(ApiDataSource)
                .packages(new String[]{ModelsMarker.class.getPackage().getName(), "com.devwue.spring.dto"})
                .properties(properties)
                .persistenceUnit("apiPU")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager apiTransactionManager(EntityManagerFactory apiEntityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager(apiEntityManagerFactory);
        return tm;
    }

    @Primary
    @Bean
    public DataSource ApiDataSource(DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @Bean
    @ConfigurationProperties("devwue.spring.api.datasource.master")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

}
