package com.ufs.engdados.domain.turma.repository.nosql;

import com.ufs.engdados.domain.turma.model.nosql.TurmaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurmaNoSqlRepository extends MongoRepository<TurmaDocument, String> {

    // Ensina o Mongo a buscar pelo id relacional da turma
    Optional<TurmaDocument> findByIdTurma(Integer idTurma);

    // Ensina o Mongo a deletar pelo id relacional da turma
    void deleteByIdTurma(Integer idTurma);

}