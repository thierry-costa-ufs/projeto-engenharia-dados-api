package com.ufs.engdados.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "universidade";
    }

    @Override
    protected void configureClientSettings(com.mongodb.MongoClientSettings.Builder builder) {
        builder.applyConnectionString(new com.mongodb.ConnectionString("mongodb://98.83.25.76:27017/universidade"));
    }

    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory,
                                                       MongoCustomConversions customConversions,
                                                       MongoMappingContext mappingContext) {

        MappingMongoConverter converter = super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);

        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }
}