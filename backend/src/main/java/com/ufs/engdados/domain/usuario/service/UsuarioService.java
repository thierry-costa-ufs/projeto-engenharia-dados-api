package com.ufs.engdados.domain.usuario.service;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.mapper.UsuarioMapper;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.model.relational.Usuario;
import com.ufs.engdados.domain.usuario.repository.nosql.UsuarioNoSqlRepository;
import com.ufs.engdados.domain.usuario.repository.relational.UsuarioRelationalRepository;
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
    public UsuarioDTO.Response criar(UsuarioDTO.Request dto) {
        Usuario usuarioPg = relationalRepository.saveAndFlush(UsuarioMapper.toPostgresEntity(dto));

        String mongoId = null;
        String statusExecucao = "SUCESSO_TOTAL";

        try {
            UsuarioDocument usuarioMg = noSqlRepository.save(UsuarioMapper.toMongoDocument(dto));
            mongoId = usuarioMg.getId();
        } catch (Exception e) {
            System.err.println("[ALERTA] Falha ao espelhar no MongoDB: " + e.getMessage());
            statusExecucao = "FALHA_PARCIAL_MONGO";
        }

        return UsuarioMapper.toResponse(usuarioPg, mongoId, statusExecucao);
    }

    @Transactional
    public UsuarioDTO.Response atualizar(Long cpf, UsuarioDTO.Request dto) {
        Usuario usuarioPg = relationalRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o CPF: " + cpf));

        usuarioPg.setNome(dto.nome());
        usuarioPg.setLogin(dto.login());

        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuarioPg.setSenha(dto.senha());
        }

        usuarioPg.setEmail(dto.email());
        usuarioPg.setTelefone(dto.telefone());

        relationalRepository.saveAndFlush(usuarioPg);

        String mongoId = null;
        String statusExecucao = "SUCESSO_TOTAL";

        try {
            var documentoExistente = noSqlRepository.findAll().stream()
                    .filter(doc -> doc.getCpf().equals(cpf))
                    .findFirst();

            UsuarioDocument usuarioMg = UsuarioMapper.toMongoDocument(dto);
            usuarioMg.setCpf(cpf);

            documentoExistente.ifPresent(doc -> usuarioMg.setId(doc.getId()));

            UsuarioDocument usuarioMgAtualizado = noSqlRepository.save(usuarioMg);
            mongoId = usuarioMgAtualizado.getId();
        } catch (Exception e) {
            System.err.println("[ALERTA] Falha ao atualizar espelho no MongoDB: " + e.getMessage());
            statusExecucao = "FALHA_PARCIAL_MONGO";
        }

        return UsuarioMapper.toResponse(usuarioPg, mongoId, statusExecucao);
    }

    public List<UsuarioDTO.Response> listarTodosRelacional() {
        return relationalRepository.findAll().stream()
                .map(UsuarioMapper::fromPostgresEntity)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO.Response> listarTodosNoSql() {
        return noSqlRepository.findAll().stream()
                .map(UsuarioMapper::fromMongoDocument)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletar(Long cpf) {
        if (relationalRepository.existsById(cpf)) {
            relationalRepository.deleteById(cpf);
        }
        noSqlRepository.deleteByCpf(cpf);
    }
}