package com.ufs.engdados.domain.departamento.service;

import com.ufs.engdados.domain.departamento.dto.DepartamentoDTO;
import com.ufs.engdados.domain.departamento.mapper.DepartamentoMapper;
import com.ufs.engdados.domain.departamento.model.nosql.DepartamentoDocument;
import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import com.ufs.engdados.domain.departamento.repository.nosql.DepartamentoNoSqlRepository;
import com.ufs.engdados.domain.departamento.repository.relational.DepartamentoRelationalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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
        Departamento departamento = relationalRepository.findById(codDepto)
                .orElseThrow(() -> new RuntimeException("Departamento de código " + codDepto + " não encontrado"));

        departamento.setNome(request.nome());
        departamento.setChefe(request.chefe());
        departamento.setOrcamento(request.orcamento());
        departamento.setComissal(request.comissal());
        relationalRepository.save(departamento);

        DepartamentoDocument document = noSqlRepository.findByCodDepto(codDepto)
                .orElseThrow(() -> new RuntimeException("Departamento de código " + codDepto + " não encontrado"));

        document.setNome(request.nome());
        document.setChefe(request.chefe());
        document.setOrcamento(request.orcamento());
        document.setComissal(request.comissal());
        noSqlRepository.save(document);

        return DepartamentoMapper.toResponse(departamento);
    }

    @Transactional
    public void delete(String codDepto){

    Departamento departamento = relationalRepository.findById(codDepto)
            .orElseThrow(() -> new RuntimeException("Departamento de código " + codDepto + " não encontrado"));
    relationalRepository.deleteById(codDepto);

    DepartamentoDocument document = noSqlRepository.findByCodDepto(codDepto)
            .orElseThrow(() -> new RuntimeException("Departamento de código " + codDepto + " não encontrado"));
    noSqlRepository.deleteById(codDepto);

    }

}