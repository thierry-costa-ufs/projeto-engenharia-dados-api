package com.ufs.engdados.domain.usuario.repository.relational;

import com.ufs.engdados.domain.usuario.model.relational.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRelationalRepository extends JpaRepository<Usuario, Long> {
}