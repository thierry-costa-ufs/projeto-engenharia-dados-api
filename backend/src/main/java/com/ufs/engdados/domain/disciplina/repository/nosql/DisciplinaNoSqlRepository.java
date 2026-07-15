package com.ufs.engdados.domain.disciplina.repository.nosql;

import com.ufs.engdados.domain.disciplina.model.nosql.DisciplinaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplinaNoSqlRepository extends MongoRepository<DisciplinaDocument, String> {

    // Ensina a buscar e deletar usando o código da disciplina, e não o _id do Mongo
    Optional<DisciplinaDocument> findByCodDisc(String codDisc);
    void deleteByCodDisc(String codDisc);
}