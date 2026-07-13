package com.ufs.engdados.domain.professor.repository.relational;

import com.ufs.engdados.domain.professor.model.relational.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRelationalRepository extends JpaRepository<Professor, String> {
}