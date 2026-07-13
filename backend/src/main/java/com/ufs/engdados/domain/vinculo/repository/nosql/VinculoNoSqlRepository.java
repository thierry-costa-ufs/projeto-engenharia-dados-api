package com.ufs.engdados.domain.vinculo.repository.nosql;

import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VinculoNoSqlRepository extends MongoRepository<VinculoDocument, String> {
    Optional<VinculoDocument> findByIdVinculo(Long idVinculo);
    void deleteByIdVinculo(Long idVinculo);
}
