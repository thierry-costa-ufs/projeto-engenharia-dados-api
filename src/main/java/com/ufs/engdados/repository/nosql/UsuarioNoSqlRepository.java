package com.ufs.engdados.repository.nosql;

import com.ufs.engdados.model.nosql.UsuarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioNoSqlRepository extends MongoRepository<UsuarioDocument, String> {
}