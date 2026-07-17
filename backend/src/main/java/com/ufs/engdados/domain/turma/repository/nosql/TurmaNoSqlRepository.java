package com.ufs.engdados.domain.turma.repository.nosql;

import com.ufs.engdados.domain.turma.model.nosql.TurmaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaNoSqlRepository extends MongoRepository<TurmaDocument, Integer> {
}