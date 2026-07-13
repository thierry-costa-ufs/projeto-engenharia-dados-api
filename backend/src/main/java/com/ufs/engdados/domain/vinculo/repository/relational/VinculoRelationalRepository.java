package com.ufs.engdados.domain.vinculo.repository.relational;

import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VinculoRelationalRepository extends JpaRepository<Vinculo, Long> {
    Optional<Vinculo> findByMatEstudante(String matEstudante);
    boolean existsByMatEstudante(String matEstudante);
    void deleteByMatEstudante(String matEstudante);
}