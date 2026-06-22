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
import java.util.Optional;

@Service
public class VinculoService {

    private final VinculoRelationalRepository relationalRepository;
    private final VinculoNoSqlRepository noSqlRepository;

    public VinculoService(VinculoRelationalRepository relationalRepository, VinculoNoSqlRepository noSqlRepository) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
    }

    @Transactional
    public VinculoDTO.Response create(VinculoDTO.Request dto) {
        Vinculo vinculoPg = VinculoMapper.toEntity(dto);
        vinculoPg = relationalRepository.save(vinculoPg);

        String mongoId = null;
        String statusExecucao = "SUCESSO_TOTAL";

        try {
            VinculoDocument vinculoMg = VinculoMapper.toDocument(dto);
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
    public Page<VinculoDTO.Response> findAllRelational(Pageable pageable) {
        Page<Vinculo> page = relationalRepository.findAll(pageable);
        List<VinculoDTO.Response> dtos = page.getContent().stream()
                .map(VinculoMapper::toResponse)
                .toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<VinculoDTO.Response> findAllNoSql(Pageable pageable) {
        Page<VinculoDocument> page = noSqlRepository.findAll(pageable);
        List<VinculoDTO.Response> dtos = page.getContent().stream()
                .map(VinculoMapper::toResponse)
                .toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Transactional
    public VinculoDTO.Response update(Long idVinculo, VinculoDTO.Request dto) {
        Vinculo vinculoPg = relationalRepository.findById(idVinculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para o ID: " + idVinculo));

        vinculoPg.setCodCurso(dto.codCurso() != null ? dto.codCurso().intValue() : null);
        vinculoPg.setDataEntrada(dto.dataEntrada());
        vinculoPg.setStatus(dto.status());
        vinculoPg.setDataSaida(dto.dataSaida());

        relationalRepository.saveAndFlush(vinculoPg);

        String mongoId = null;
        String statusExecucao = "SUCESSO_TOTAL";

        try {
            Optional<VinculoDocument> documentoExistente = noSqlRepository.findByIdRelacional(idVinculo);

            VinculoDocument vinculoMg = VinculoMapper.toDocument(dto);
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
    public void delete(Long idVinculo) {
        if (relationalRepository.existsById(idVinculo)) {
            relationalRepository.deleteById(idVinculo);
        }
        noSqlRepository.deleteByIdRelacional(idVinculo);
    }
}