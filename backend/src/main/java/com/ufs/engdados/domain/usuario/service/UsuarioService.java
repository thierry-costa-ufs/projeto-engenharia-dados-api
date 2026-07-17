package com.ufs.engdados.domain.usuario.service;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.mapper.UsuarioMapper;
import com.ufs.engdados.domain.usuario.model.relational.Usuario;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.repository.nosql.UsuarioNoSqlRepository;
import com.ufs.engdados.domain.usuario.repository.relational.UsuarioRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRelationalRepository relationalRepository;
    private final UsuarioNoSqlRepository noSqlRepository;

    public UsuarioService(UsuarioRelationalRepository relationalRepository,
                          UsuarioNoSqlRepository noSqlRepository) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
    }

    @Transactional
    public UsuarioDTO.Response create(UsuarioDTO.Request dto) {
        relationalRepository.save(UsuarioMapper.toEntity(dto));
        UsuarioDocument doc = noSqlRepository.save(UsuarioMapper.toDocument(dto));

        return UsuarioMapper.toResponse(doc);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO.Response> findAllRelational(Pageable pageable) {
        Page<Usuario> usuarios = relationalRepository.findAll(pageable);

        return usuarios.map(usuario -> UsuarioMapper.toResponse(usuario));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO.Response> findAllNoSql(Pageable pageable) {
        Page<UsuarioDocument> usuarios = noSqlRepository.findAll(pageable);

        return usuarios.map(usuario -> UsuarioMapper.toResponse(usuario));
    }

    @Transactional
    public UsuarioDTO.Response update(Long cpf, UsuarioDTO.Request dto) {
        if(!(cpf.equals(dto.cpf()))){
            throw new IllegalArgumentException("O cpf da URL não corresponde ao cpf enviada no corpo da requisição.");
        }

        Usuario usuario = relationalRepository.findById(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário " + cpf + " não encontrado"));

        UsuarioMapper.updateEntity(dto, usuario);
        relationalRepository.save(usuario);

        UsuarioDocument document = noSqlRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário " + cpf + " não encontrado"));

        UsuarioMapper.updateDocument(dto, document);
        noSqlRepository.save(document);

        return UsuarioMapper.toResponse(document);
    }

    @Transactional
    public void delete(Long cpf) {
        relationalRepository.findById(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário " + cpf + " não encontrado"));
        relationalRepository.deleteById(cpf);

        noSqlRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário " + cpf + " não encontrado"));
        noSqlRepository.deleteByCpf(cpf);
    }
}