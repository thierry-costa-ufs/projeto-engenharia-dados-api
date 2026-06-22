package com.ufs.engdados.domain.estudante.repository.nosql;

import com.ufs.engdados.domain.estudante.model.nosql.EstudanteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudanteNoSqlRepository extends MongoRepository<EstudanteDocument, String> {
    Optional<EstudanteDocument> findByMatEstudante(String matEstudante);
    void deleteByMatEstudante(String matEstudante);
}
