package com.ufs.engdados.domain.usuario.repository.nosql;

import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioNoSqlRepository extends MongoRepository<UsuarioDocument, String> {
    List<UsuarioDocument> findByCpfIn(List<Long> cpfs);
    Optional<UsuarioDocument> findByCpf(Long cpf);
    void deleteByCpf(Long cpf);

    Page<UsuarioDocument> findByPerfilProfessorIsNotNull(Pageable pageable);
}