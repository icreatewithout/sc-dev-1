package com.ifeb2.scdevseata;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class SeataConfiguration {


    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSourceDelegation(DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        log.info("seata data source proxy init .");
        return new DataSourceProxy(dataSource);
    }

}
