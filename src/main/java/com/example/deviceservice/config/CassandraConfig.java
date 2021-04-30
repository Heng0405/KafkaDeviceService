package com.example.deviceservice.config;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    private final CassandraProperties cassandraProperties;

    public CassandraConfig(CassandraProperties cassandraProperties) {
        this.cassandraProperties = cassandraProperties;
    }

    @Override
    protected String getKeyspaceName() {
        return cassandraProperties.getKeyspaceName();
    }

    @Override
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean bean = new CqlSessionFactoryBean();
        bean.setContactPoints(String.join(",", cassandraProperties.getContactPoints()));
        bean.setKeyspaceName(cassandraProperties.getKeyspaceName());
        bean.setLocalDatacenter(cassandraProperties.getLocalDatacenter());
        bean.setPort(cassandraProperties.getPort());
        bean.setUsername(cassandraProperties.getUsername());
        bean.setPassword(cassandraProperties.getPassword());
        return bean;
    }
}
