package com.ufs.engdados.domain.professor.service;

import com.ufs.engdados.domain.professor.dto.ProfessorDTO;
import com.ufs.engdados.domain.professor.event.ProfessorDeletadoEvent;
import com.ufs.engdados.domain.professor.event.ProfessorSalvoEvent;
import com.ufs.engdados.domain.professor.mapper.ProfessorMapper;
import com.ufs.engdados.domain.professor.model.nosql.ProfessorDocument;
import com.ufs.engdados.domain.professor.model.relational.Professor;
import com.ufs.engdados.domain.professor.repository.nosql.ProfessorNoSqlRepository;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.repository.nosql.UsuarioNoSqlRepository;
import com.ufs.engdados.domain.professor.repository.relational.ProfessorRelationalRepository;
import com.ufs.engdados.domain.usuario.repository.relational.UsuarioRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    private final ProfessorRelationalRepository relationalRepository;
    private final ProfessorNoSqlRepository noSqlRepository;

    public ProfessorService(ProfessorRelationalRepository relationalRepository,
                            ProfessorNoSqlRepository noSqlRepository) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
    }

    @Transactional
    public ProfessorDTO.Response create(ProfessorDTO.Request dto) {
        relationalRepository.save(ProfessorMapper.toEntity(dto));
        ProfessorDocument prof = noSqlRepository.save(ProfessorMapper.toDocument(dto));

        return ProfessorMapper.toResponse(prof);
    }

    @Transactional(readOnly = true)
    public Page<ProfessorDTO.Response> findAllRelational(Pageable pageable) {
        Page<Professor> professores = relationalRepository.findAll(pageable);

        return professores.map(professor -> ProfessorMapper.toResponse(professor));
    }

    @Transactional(readOnly = true)
    public Page<ProfessorDTO.Response> findAllNoSql(Pageable pageable) {
        Page<ProfessorDocument> professores = noSqlRepository.findAll(pageable);

        return professores.map(professor -> ProfessorMapper.toResponse(professor));
    }

    @Transactional
    public ProfessorDTO.Response update(String matricula, ProfessorDTO.Request dto) {
        if(!(matricula.equals(dto.matricula()))){
            throw new IllegalArgumentException("A matrícula da URL não corresponde a matrícula enviada no corpo da requisição.");
        }

        Professor prof = relationalRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Professor " + matricula + " não encontrado"));

        ProfessorMapper.updateEntity(dto, prof);
        relationalRepository.save(prof);

        ProfessorDocument doc = noSqlRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Professor " + matricula + " não encontrado"));

        ProfessorMapper.updateDocument(dto, doc);
        return ProfessorMapper.toResponse(doc);
    }

    @Transactional
    public void delete(String matricula) {
        relationalRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Professor " + matricula + " não encontrado"));
        relationalRepository.deleteById(matricula);

        noSqlRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Professor " + matricula + " não encontrado"));
        noSqlRepository.findByMatricula(matricula);
    }

}