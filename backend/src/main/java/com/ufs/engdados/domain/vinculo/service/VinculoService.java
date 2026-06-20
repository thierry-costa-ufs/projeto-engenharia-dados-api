package com.ufs.engdados.domain.vinculo.service;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.mapper.VinculoMapper;
import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;
import com.ufs.engdados.domain.vinculo.repository.nosql.VinculoNoSqlRepository;
import com.ufs.engdados.domain.vinculo.repository.relational.VinculoRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VinculoService {

    private final VinculoRelationalRepository relationalRepository;
    private final VinculoNoSqlRepository noSqlRepository;

    public VinculoService(VinculoRelationalRepository relationalRepository, VinculoNoSqlRepository noSqlRepository) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
    }

    @Transactional
    public VinculoDTO.Response criar(VinculoDTO.Request dto) {
        Vinculo vinculoPg = VinculoMapper.toPostgresEntity(dto);
        vinculoPg = relationalRepository.save(vinculoPg);

        String mongoId = null;
        String statusExecucao = "SUCESSO_TOTAL";

        try {
            VinculoDocument vinculoMg = VinculoMapper.toMongoDocument(dto);
            // GARANTIDO: Vincula o ID gerado pelo Postgres no documento do MongoDB
            vinculoMg.setIdRelacional(vinculoPg.getIdVinculo());

            vinculoMg = noSqlRepository.save(vinculoMg);
            mongoId = vinculoMg.getId();
        } catch (Exception e) {
            System.err.println("[ALERTA] Falha ao espelhar vínculo no MongoDB: " + e.getMessage());
            statusExecucao = "FALHA_PARCIAL_MONGO";
        }

        return VinculoMapper.toResponse(vinculoPg, mongoId, statusExecucao);
    }

    @Transactional(readOnly = true)
    public Page<VinculoDTO.Response> listarTodosRelacional(Pageable pageable) {
        Page<Vinculo> page = relationalRepository.findAllNativo(pageable);
        List<VinculoDTO.Response> dtos = page.getContent().stream()
                .map(VinculoMapper::fromPostgresEntity)
                .toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<VinculoDTO.Response> listarTodosNoSql(Pageable pageable) {
        Page<VinculoDocument> page = noSqlRepository.findAll(pageable);
        List<VinculoDTO.Response> dtos = page.getContent().stream()
                .map(VinculoMapper::fromMongoDocument)
                .toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Transactional
    public VinculoDTO.Response atualizar(String matEstudante, VinculoDTO.Request dto) {
        // CORRIGIDO: Utiliza findByMatEstudante em vez do findById numérico
        Vinculo vinculoPg = relationalRepository.findByMatEstudante(matEstudante)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para a matrícula: " + matEstudante));

        vinculoPg.setCodCurso(dto.codCurso() != null ? dto.codCurso().intValue() : null);
        vinculoPg.setSituacao(dto.situacao());
        relationalRepository.saveAndFlush(vinculoPg);

        String mongoId = null;
        String statusExecucao = "SUCESSO_TOTAL";

        try {
            var documentoExistente = noSqlRepository.findAll().stream()
                    .filter(doc -> matEstudante.equals(doc.getMatEstudante()))
                    .findFirst();

            VinculoDocument vinculoMg = VinculoMapper.toMongoDocument(dto);
            vinculoMg.setIdRelacional(vinculoPg.getIdVinculo());
            documentoExistente.ifPresent(doc -> vinculoMg.setId(doc.getId()));

            VinculoDocument vinculoMgAtualizado = noSqlRepository.save(vinculoMg);
            mongoId = vinculoMgAtualizado.getId();
        } catch (Exception e) {
            System.err.println("[ALERTA] Falha ao atualizar espelho no MongoDB: " + e.getMessage());
            statusExecucao = "FALHA_PARCIAL_MONGO";
        }

        return VinculoMapper.toResponse(vinculoPg, mongoId, statusExecucao);
    }

    @Transactional
    public void deletar(String matEstudante) {
        // CORRIGIDO: Utiliza os novos métodos por matrícula do repositório relacional
        if (relationalRepository.existsByMatEstudante(matEstudante)) {
            relationalRepository.deleteByMatEstudante(matEstudante);
        }
        noSqlRepository.findAll().stream()
                .filter(doc -> matEstudante.equals(doc.getMatEstudante()))
                .forEach(doc -> noSqlRepository.deleteById(doc.getId()));
    }
}