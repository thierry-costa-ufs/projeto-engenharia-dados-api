package com.ufs.engdados.domain.departamento.repository.nosql;

import com.ufs.engdados.domain.departamento.model.nosql.DepartamentoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartamentoNoSqlRepository extends MongoRepository<DepartamentoDocument, String>{
    Optional<DepartamentoDocument> findByCodDepto(String codDepto);
    void deleteByCodDepto(String codDepto);
}