package com.ufs.engdados.domain.cursa.repository.nosql;

import com.ufs.engdados.domain.cursa.model.nosql.CursaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursaNoSqlRepository extends MongoRepository<CursaDocument, String> {}