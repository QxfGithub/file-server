package com.qxf.fileserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty("spring.datasource.fund.url")
public class DataSourceConfiguration {

    @Bean(name = "fundDataSource", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties("spring.datasource.fund")
    public DataSource fundDataSource() {
        return new DruidDataSource();
    }

}
