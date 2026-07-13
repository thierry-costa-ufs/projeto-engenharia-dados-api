package com.ufs.engdados.domain.vinculo.service;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.mapper.VinculoMapper;
import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;
import com.ufs.engdados.domain.vinculo.repository.nosql.VinculoNoSqlRepository;
import com.ufs.engdados.domain.vinculo.repository.relational.VinculoRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VinculoService {

    private final VinculoRelationalRepository relationalRepository;
    private final VinculoNoSqlRepository noSqlRepository;
    private final EntityManager entityManager;

    public VinculoService(VinculoRelationalRepository relationalRepository,
                          VinculoNoSqlRepository noSqlRepository,
                          EntityManager entityManager) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    @Transactional
    public void consertarContadorDaAWS() {
        try {
            String sql = "SELECT setval(pg_get_serial_sequence('universidade.vinculo', 'id_vinculo'), COALESCE((SELECT MAX(id_vinculo) FROM universidade.vinculo), 1))";
            entityManager.createNativeQuery(sql).getSingleResult();
            System.out.println("Contador do PostgreSQL (Vinculo) sincronizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Aviso: Não foi possível sincronizar o contador de Vinculo. " + e.getMessage());
        }
    }

    @Transactional
    public VinculoDTO.Response create(VinculoDTO.Request dto) {
        Vinculo salvoRelacional = relationalRepository.save(VinculoMapper.toEntity(dto));

        VinculoDocument document = VinculoMapper.toDocument(dto);

        document.setIdVinculo(salvoRelacional.getIdVinculo());

        VinculoDocument salvoNoSql = noSqlRepository.save(document);

        return VinculoMapper.toResponse(salvoNoSql);
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
        if(dto.idVinculo() != null && !(idVinculo.equals(dto.idVinculo()))){
            throw new IllegalArgumentException("O id da URL não corresponde ao id enviado no corpo da requisição.");
        }

        Vinculo vinculoPg = relationalRepository.findById(idVinculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para o ID: " + idVinculo));

        VinculoMapper.updateEntity(dto, vinculoPg);
        relationalRepository.save(vinculoPg);

        VinculoDocument documentpg = noSqlRepository.findByIdVinculo(idVinculo)
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

        noSqlRepository.findByIdVinculo(idVinculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado para o ID: " + idVinculo));
        noSqlRepository.deleteByIdVinculo(idVinculo);
    }
}
