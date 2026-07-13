package com.ufs.engdados.domain.estudante.repository.relational;

import com.ufs.engdados.domain.estudante.model.relational.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudanteRelationalRepository extends JpaRepository<Estudante, String> {
}