package com.ufs.engdados.domain.vinculo.repository.nosql;

import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface VinculoNoSqlRepository extends MongoRepository<VinculoDocument, String> {
    Optional<VinculoDocument> findByIdRelacional(Long idRelacional);
    void deleteByIdRelacional(Long idRelacional);
}