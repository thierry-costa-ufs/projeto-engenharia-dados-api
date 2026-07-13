package com.ufs.engdados.domain.disciplina.repository.relational;

import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRelationalRepository extends JpaRepository<Disciplina, String> {
    // O Spring Data JPA já implementa automaticamente todos os métodos básicos (save, findById, delete, etc.)
}