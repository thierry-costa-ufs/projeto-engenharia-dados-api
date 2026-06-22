package com.ufs.engdados.domain.professor.repository.relational;

import com.ufs.engdados.domain.professor.model.relational.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRelationalRepository extends JpaRepository<Professor, Long> {

    @Query(value = "SELECT p FROM Professor p JOIN FETCH p.usuario",
            countQuery = "SELECT count(p) FROM Professor p")
    Page<Professor> findAllEager(Pageable pageable);
}