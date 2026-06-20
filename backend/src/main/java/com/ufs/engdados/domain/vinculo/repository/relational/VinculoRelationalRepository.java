package com.ufs.engdados.domain.vinculo.repository.relational;

import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

// CORRIGIDO: Alterado de String para Long
public interface VinculoRelationalRepository extends JpaRepository<Vinculo, Long> {

    // Métodos necessários para buscar e remover usando a matrícula como chave lógica
    Optional<Vinculo> findByMatEstudante(String matEstudante);
    boolean existsByMatEstudante(String matEstudante);
    void deleteByMatEstudante(String matEstudante);

    // Ajustado para selecionar as colunas reais mapeadas no banco relacional
    @Query(value = "SELECT idvinculo, mat_estudante, curso, status FROM universidade.vinculo",
            countQuery = "SELECT count(*) FROM universidade.vinculo",
            nativeQuery = true)
    Page<Vinculo> findAllNativo(Pageable pageable);
}