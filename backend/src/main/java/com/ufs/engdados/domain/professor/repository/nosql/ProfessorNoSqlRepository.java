package com.ufs.engdados.domain.professor.repository.nosql;

import com.ufs.engdados.domain.professor.model.nosql.ProfessorDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorNoSqlRepository extends MongoRepository<ProfessorDocument, String> {

    Optional<ProfessorDocument> findByCpf(Long cpf);

    List<ProfessorDocument> findByCpfIn(List<Long> cpfs);
}