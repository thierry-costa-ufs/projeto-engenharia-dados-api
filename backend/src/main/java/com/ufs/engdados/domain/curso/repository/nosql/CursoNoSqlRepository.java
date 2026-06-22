package com.ufs.engdados.domain.curso.repository.nosql;

import com.ufs.engdados.domain.curso.model.nosql.CursoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CursoNoSqlRepository extends MongoRepository<CursoDocument, Integer> {
    List<CursoDocument> findByIdCursoIn(List<Integer> idsCursos);
}