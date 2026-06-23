package com.ufs.engdados.domain.estudante.service;

import com.ufs.engdados.domain.estudante.dto.EstudanteDTO;
import com.ufs.engdados.domain.estudante.mapper.EstudanteMapper;
import com.ufs.engdados.domain.estudante.model.nosql.EstudanteDocument;
import com.ufs.engdados.domain.estudante.model.relational.Estudante;
import com.ufs.engdados.domain.estudante.repository.nosql.EstudanteNoSqlRepository;
import com.ufs.engdados.domain.estudante.repository.relational.EstudanteRelationalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstudanteService{

    private final EstudanteRelationalRepository relationalRepository;
    private final EstudanteNoSqlRepository noSqlRepository;

    public EstudanteService(EstudanteRelationalRepository relationalRepository, EstudanteNoSqlRepository noSqlRepository, EstudanteMapper estudanteMapper){
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
    }

    @Transactional
    public EstudanteDTO.Response create(EstudanteDTO.Request request){
        relationalRepository.save(EstudanteMapper.toEntity(request));
        EstudanteDocument salvoNoSql = noSqlRepository.save(EstudanteMapper.toDocument(request));

        return EstudanteMapper.toResponse(salvoNoSql);
    }

    @Transactional(readOnly = true)
    public Page<EstudanteDTO.Response> findAllRelational(Pageable pageable){
        Page<Estudante> estudantes = relationalRepository.findAll(pageable);

        return estudantes.map(estudante -> EstudanteMapper.toResponse(estudante));
    }

    @Transactional(readOnly = true)
    public Page<EstudanteDTO.Response> findAllNoSql(Pageable pageable){
        Page<EstudanteDocument> estudantes = noSqlRepository.findAll(pageable);

        return estudantes.map(estudante -> EstudanteMapper.toResponse(estudante));
    }

    @Transactional
    public EstudanteDTO.Response update(String matricula, EstudanteDTO.Request request) {
        Estudante estudante = relationalRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Estudante " + matricula + " não encontrado"));

        estudante.setMc(request.mc());
        relationalRepository.save(estudante);

        EstudanteDocument document = noSqlRepository.findByMatEstudante(matricula)
                .orElseThrow(() -> new RuntimeException("Estudante " + matricula + " não encontrado"));

        document.setMc(request.mc());
        noSqlRepository.save(document);

        return EstudanteMapper.toResponse(estudante);
    }

    @Transactional
    public void delete(String matricula){

        relationalRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Estudante " + matricula + " não encontrado"));
        relationalRepository.deleteById(matricula);

        noSqlRepository.findByMatEstudante(matricula)
                .orElseThrow(() -> new RuntimeException("Estudante " + matricula + " não encontrado"));
        noSqlRepository.deleteByMatEstudante(matricula);

    }

}