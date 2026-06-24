package com.ufs.engdados.domain.departamento.repository.relational;

import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRelationalRepository extends JpaRepository<Departamento, String> {
}