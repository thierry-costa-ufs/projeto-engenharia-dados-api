package com.ufs.engdados.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
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

    // Lê a configuração dinamicamente do application.properties
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        return "universidade";
    }

    @Override
    protected void configureClientSettings(com.mongodb.MongoClientSettings.Builder builder) {
        builder.applyConnectionString(new com.mongodb.ConnectionString(mongoUri));
    }

    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory,
                                                       MongoCustomConversions customConversions,
                                                       MongoMappingContext mappingContext) {

        MappingMongoConverter converter = super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);

        // Remove a criação da coluna "_class" que polui os documentos do MongoDB
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }
}