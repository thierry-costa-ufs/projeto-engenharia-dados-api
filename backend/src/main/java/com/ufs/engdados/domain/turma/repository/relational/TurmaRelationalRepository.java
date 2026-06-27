package com.ufs.engdados.domain.turma.repository.relational;

import com.ufs.engdados.domain.turma.model.relational.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRelationalRepository extends JpaRepository<Turma, Integer> {
}