package com.ufs.engdados.domain.usuario.service;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.event.UsuarioDeletadoEvent;
import com.ufs.engdados.domain.usuario.event.UsuarioSalvoEvent;
import com.ufs.engdados.domain.usuario.mapper.UsuarioMapper;
import com.ufs.engdados.domain.usuario.model.relational.Usuario;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.repository.nosql.UsuarioNoSqlRepository;
import com.ufs.engdados.domain.usuario.repository.relational.UsuarioRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRelationalRepository relationalRepository;
    private final UsuarioNoSqlRepository noSqlRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UsuarioService(UsuarioRelationalRepository relationalRepository, UsuarioNoSqlRepository noSqlRepository, ApplicationEventPublisher eventPublisher) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UsuarioDTO.Response criar(UsuarioDTO.Request dto) {
        Usuario usuarioPg = relationalRepository.save(UsuarioMapper.toPostgresEntity(dto));
        eventPublisher.publishEvent(new UsuarioSalvoEvent(dto, usuarioPg, false));
        return UsuarioMapper.toResponse(usuarioPg);
    }

    @Transactional
    public UsuarioDTO.Response atualizar(Long cpf, UsuarioDTO.Request dto) {
        if (!cpf.equals(dto.cpf())) {
            throw new IllegalArgumentException("O CPF da URL não corresponde ao CPF enviado no corpo da requisição.");
        }
        Usuario usuarioPg = relationalRepository.findById(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o CPF: " + cpf));
        usuarioPg.setNome(dto.nome());
        usuarioPg.setLogin(dto.login());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuarioPg.setSenha(dto.senha());
        }
        usuarioPg.setEmail(dto.email());
        usuarioPg.setTelefone(dto.telefone());
        Usuario usuarioAtualizado = relationalRepository.save(usuarioPg);
        eventPublisher.publishEvent(new UsuarioSalvoEvent(dto, usuarioAtualizado, true));
        return UsuarioMapper.toResponse(usuarioAtualizado);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO.Response> listarTodosRelacional(Pageable pageable) {
        Page<Usuario> usuariosPg = relationalRepository.findAll(pageable);
        List<Long> cpfs = usuariosPg.getContent().stream().map(Usuario::getCpf).toList();
        List<UsuarioDocument> documentos = noSqlRepository.findByCpfIn(cpfs);
        Map<Long, UsuarioDocument> mongoMap = documentos.stream()
                .collect(Collectors.toMap(UsuarioDocument::getCpf, doc -> doc));
        return usuariosPg.map(usuario -> {
            UsuarioDocument doc = mongoMap.get(usuario.getCpf());
            if (doc != null) {
                return UsuarioMapper.toResponse(usuario, doc.getId(), "INTEGRADO_NOSQL");
            }
            return UsuarioMapper.toResponse(usuario);
        });
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO.Response> listarTodosNoSql(Pageable pageable) {
        return noSqlRepository.findAll(pageable)
                .map(UsuarioMapper::fromMongoDocument);
    }

    @Transactional
    public void deletar(Long cpf) {
        if (!relationalRepository.existsById(cpf)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o CPF: " + cpf);
        }
        relationalRepository.deleteById(cpf);
        eventPublisher.publishEvent(new UsuarioDeletadoEvent(cpf));
    }
}