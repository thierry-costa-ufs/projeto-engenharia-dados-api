package com.ufs.engdados.domain.usuario.config; // Certifique-se de ajustar para o package correto do seu projeto

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    // Injeta a propriedade do ficheiro application.properties com um fallback de segurança
    @Value("${spring.data.mongodb.database:engdados_db}")
    private String databaseName;

    // Injeta a URI de conexão do application.properties
    @Value("${spring.data.mongodb.uri:mongodb://localhost:27017/engdados_db}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        // Força explicitamente o uso do nome engdados_db
        return databaseName;
    }

    @Override
    public MongoClient mongoClient() {
        // Cria o cliente de ligação ignorando qualquer configuração oculta do Spring
        return MongoClients.create(mongoUri);
    }

    @Override
    protected boolean autoIndexCreation() {
        // Boa prática para ambiente de desenvolvimento: cria os índices automaticamente
        return true;
    }
}