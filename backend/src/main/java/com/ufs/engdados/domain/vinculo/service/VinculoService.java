package com.ufs.engdados.domain.vinculo.service;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.mapper.VinculoMapper;
import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;
import com.ufs.engdados.domain.vinculo.repository.nosql.VinculoNoSqlRepository;
import com.ufs.engdados.domain.vinculo.repository.relational.VinculoRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
         relationalRepository.save(VinculoMapper.toEntity(dto));
         VinculoDocument document = noSqlRepository.save(VinculoMapper.toDocument(dto));

         return VinculoMapper.toResponse(document);
    }

    @Transactional(readOnly = true)
    public Page<VinculoDTO.Response> findAllRelational(Pageable pageable) {
        Page<Vinculo> page = relationalRepository.findAll(pageable);

        return page.map(vinculo -> VinculoMapper.toResponse(vinculo));
    }

    @Transactional(readOnly = true)
    public Page<VinculoDTO.Response> findAllNoSql(Pageable pageable) {
        Page<VinculoDocument> page = noSqlRepository.findAll(pageable);

        return page.map(vinculo -> VinculoMapper.toResponse(vinculo));
    }

    @Transactional
    public VinculoDTO.Response update(Long idVinculo, VinculoDTO.Request dto) {
        if(!(idVinculo.equals(dto.idVinculo()))){
            throw new IllegalArgumentException("O id da URL não corresponde ao id enviada no corpo da requisição.");
        }

        Vinculo vinculoPg = relationalRepository.findById(idVinculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para o ID: " + idVinculo));

        VinculoMapper.updateEntity(dto, vinculoPg);
        relationalRepository.save(vinculoPg);

        VinculoDocument documentpg = noSqlRepository.findByidVinculo(idVinculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para o ID: " + idVinculo));

        VinculoMapper.updateDocument(dto, documentpg);
        noSqlRepository.save(documentpg);

        return VinculoMapper.toResponse(documentpg);
    }

    @Transactional
    public void delete(Long idVinculo) {
        relationalRepository.findById(idVinculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para o ID: " + idVinculo));
        relationalRepository.deleteById(idVinculo);

        noSqlRepository.findByidVinculo(idVinculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para o ID: " + idVinculo));
        noSqlRepository.deleteByIdVinculo(idVinculo);
    }
}