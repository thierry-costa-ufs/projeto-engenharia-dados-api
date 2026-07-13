package com.ufs.engdados.domain.curso.repository.relational;

import com.ufs.engdados.domain.curso.model.relational.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRelationalRepository extends JpaRepository<Curso, Integer> {
}