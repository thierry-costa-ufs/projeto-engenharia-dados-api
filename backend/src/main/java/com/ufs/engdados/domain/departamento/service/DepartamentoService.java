package com.ufs.engdados.domain.departamento.service;

import com.ufs.engdados.domain.departamento.dto.DepartamentoDTO;
import com.ufs.engdados.domain.departamento.mapper.DepartamentoMapper;
import com.ufs.engdados.domain.departamento.model.nosql.DepartamentoDocument;
import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import com.ufs.engdados.domain.departamento.repository.nosql.DepartamentoNoSqlRepository;
import com.ufs.engdados.domain.departamento.repository.relational.DepartamentoRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoService{

    private final DepartamentoRelationalRepository relationalRepository;
    private final DepartamentoNoSqlRepository noSqlRepository;

    public DepartamentoService(DepartamentoRelationalRepository relationalRepository, DepartamentoNoSqlRepository noSqlRepository){
        this.relationalRepository =relationalRepository;
        this.noSqlRepository = noSqlRepository;
    }

    @Transactional
    public DepartamentoDTO.Response create(DepartamentoDTO.Request request){
        relationalRepository.save(DepartamentoMapper.toEntity(request));
        DepartamentoDocument salvoNoSql = noSqlRepository.save(DepartamentoMapper.toDocument(request));

        return DepartamentoMapper.toResponse(salvoNoSql);
    }

    @Transactional(readOnly = true)
    public Page<DepartamentoDTO.Response> findAllRelational(Pageable pageable){
        Page<Departamento> departamentos = relationalRepository.findAll(pageable);

        return departamentos.map(departamento -> DepartamentoMapper.toResponse(departamento));
    }

    @Transactional(readOnly = true)
    public Page<DepartamentoDTO.Response> findAllNoSql(Pageable pageable){
        Page<DepartamentoDocument> departamentos = noSqlRepository.findAll(pageable);

        return departamentos.map(departamento -> DepartamentoMapper.toResponse(departamento));
    }

    @Transactional
    public DepartamentoDTO.Response update(String codDepto, DepartamentoDTO.Request request){
        if(!(codDepto.equals(request.codDepto()))){
            throw new IllegalArgumentException("O código de departamento da URL não corresponde ao código de departamento enviada no corpo da requisição.");
        }

        Departamento departamento = relationalRepository.findById(codDepto)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento de código " + codDepto + " não encontrado"));

        DepartamentoMapper.updateEntity(request, departamento);
        relationalRepository.save(departamento);

        DepartamentoDocument document = noSqlRepository.findByCodDepto(codDepto)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento de código " + codDepto + " não encontrado"));

        DepartamentoMapper.updateDocument(request, document);
        noSqlRepository.save(document);

        return DepartamentoMapper.toResponse(document);
    }

    @Transactional
    public void delete(String codDepto){

    relationalRepository.findById(codDepto)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento de código " + codDepto + " não encontrado"));
    relationalRepository.deleteById(codDepto);

    noSqlRepository.findByCodDepto(codDepto)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento de código " + codDepto + " não encontrado"));
    noSqlRepository.deleteByCodDepto(codDepto);

    }

}