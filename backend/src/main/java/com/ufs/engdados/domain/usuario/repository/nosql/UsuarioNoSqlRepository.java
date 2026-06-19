package com.ufs.engdados.domain.usuario.repository.nosql;

import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioNoSqlRepository extends MongoRepository<UsuarioDocument, String> {
    Optional<UsuarioDocument> findByCpf(Long cpf);
    void deleteByCpf(Long cpf);
}