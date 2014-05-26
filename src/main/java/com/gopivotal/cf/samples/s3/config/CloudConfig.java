package com.gopivotal.cf.samples.s3.config;

import com.gopivotal.cf.samples.s3.repository.S3;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

    @Bean
    public DataSource dataSource() {
        // Default pool size to 4 connections to support ClearDB Spark (free)
        PooledServiceConnectorConfig.PoolConfig poolConfig =
                new PooledServiceConnectorConfig.PoolConfig(4, 200);

        DataSourceConfig config = new DataSourceConfig(poolConfig, new DataSourceConfig.ConnectionConfig(""));

        return connectionFactory().dataSource(config);
    }

    @Bean
    public S3 s3() {
        return connectionFactory().service(S3.class);
    }

}
