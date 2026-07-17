package com.ufs.engdados.domain.disciplina.repository.nosql;

import com.ufs.engdados.domain.disciplina.model.nosql.DisciplinaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaNoSqlRepository extends MongoRepository<DisciplinaDocument, String> {
    // O Spring Data MongoDB cuida de toda a persistência no NoSQL
}