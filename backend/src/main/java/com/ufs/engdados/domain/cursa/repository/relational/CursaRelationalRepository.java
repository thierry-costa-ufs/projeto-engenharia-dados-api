package com.ufs.engdados.domain.cursa.repository.relational;

import com.ufs.engdados.domain.cursa.model.relational.Cursa;
import com.ufs.engdados.domain.cursa.model.relational.CursaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursaRelationalRepository extends JpaRepository<Cursa, CursaId> {}