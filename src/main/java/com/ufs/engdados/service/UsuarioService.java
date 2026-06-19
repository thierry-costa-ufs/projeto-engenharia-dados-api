package com.ufs.engdados.service;

import com.ufs.engdados.dto.UsuarioDTO;
import com.ufs.engdados.model.nosql.UsuarioDocument;
import com.ufs.engdados.model.relational.Usuario;
import com.ufs.engdados.repository.nosql.UsuarioNoSqlRepository;
import com.ufs.engdados.repository.relational.UsuarioRelationalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRelationalRepository relationalRepository;
    private final UsuarioNoSqlRepository noSqlRepository;

    public UsuarioService(UsuarioRelationalRepository relationalRepository, UsuarioNoSqlRepository noSqlRepository) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
    }

    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {
        // 1. Salva no PostgreSQL (Relacional)
        Usuario usuarioPg = new Usuario();
        usuarioPg.setNome(dto.getNome());
        usuarioPg.setEmail(dto.getEmail());
        usuarioPg.setSenha(dto.getSenha());
        usuarioPg = relationalRepository.save(usuarioPg);

        // 2. Salva no MongoDB (NoSQL)
        UsuarioDocument usuarioMg = new UsuarioDocument();
        usuarioMg.setNome(dto.getNome());
        usuarioMg.setEmail(dto.getEmail());
        usuarioMg.setSenha(dto.getSenha());
        usuarioMg = noSqlRepository.save(usuarioMg);

        // 3. Retorna o DTO com os IDs gerados por ambos os bancos
        return new UsuarioDTO(usuarioPg.getId(), usuarioMg.getId(), usuarioPg.getNome(), usuarioPg.getEmail(), null);
    }

    public List<UsuarioDTO> listarTodosRelacional() {
        return relationalRepository.findAll().stream()
                .map(u -> new UsuarioDTO(u.getId(), null, u.getNome(), u.getEmail(), null))
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> listarTodosNoSql() {
        return noSqlRepository.findAll().stream()
                .map(doc -> new UsuarioDTO(null, doc.getId(), doc.getNome(), doc.getEmail(), null))
                .collect(Collectors.toList());
    }
}