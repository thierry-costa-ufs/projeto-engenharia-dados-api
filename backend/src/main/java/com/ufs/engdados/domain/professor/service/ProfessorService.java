package com.ufs.engdados.domain.professor.service;

import com.ufs.engdados.domain.professor.dto.ProfessorDTO;
import com.ufs.engdados.domain.professor.event.ProfessorDeletadoEvent;
import com.ufs.engdados.domain.professor.event.ProfessorSalvoEvent;
import com.ufs.engdados.domain.professor.mapper.ProfessorMapper;
import com.ufs.engdados.domain.professor.model.relational.Professor;
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
    private final UsuarioRelationalRepository usuarioRelationalRepository;
    private final UsuarioNoSqlRepository usuarioNoSqlRepository; // Substituído o repositório isolado pelo de Usuário
    private final ProfessorMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public ProfessorService(ProfessorRelationalRepository relationalRepository,
                            UsuarioRelationalRepository usuarioRelationalRepository,
                            UsuarioNoSqlRepository usuarioNoSqlRepository,
                            ProfessorMapper mapper,
                            ApplicationEventPublisher eventPublisher) {
        this.relationalRepository = relationalRepository;
        this.usuarioRelationalRepository = usuarioRelationalRepository;
        this.usuarioNoSqlRepository = usuarioNoSqlRepository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ProfessorDTO.Response create(ProfessorDTO.Request dto) {
        var usuario = usuarioRelationalRepository.findById(dto.cpf())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário base não encontrado para o CPF: " + dto.cpf()));

        Professor prof = mapper.toEntity(dto);
        prof.setUsuario(usuario);
        prof = relationalRepository.save(prof);

        eventPublisher.publishEvent(new ProfessorSalvoEvent(prof.getCpf(), dto));
        return mapper.toResponse(prof, "ASSINCRONO");
    }

    @Transactional(readOnly = true)
    public Page<ProfessorDTO.Response> findAllRelational(Pageable pageable) {
        Page<Professor> professoresPg = relationalRepository.findAllEager(pageable);

        List<Long> cpfs = professoresPg.getContent().stream().map(Professor::getCpf).toList();

        List<UsuarioDocument> usuariosDoc = cpfs.isEmpty()
                ? List.of()
                : usuarioNoSqlRepository.findByCpfIn(cpfs);

        Map<Long, UsuarioDocument> mongoMap = usuariosDoc.stream()
                .filter(doc -> doc != null && doc.getCpf() != null)
                .collect(Collectors.toMap(UsuarioDocument::getCpf, doc -> doc, (a, b) -> a));

        return professoresPg.map(prof -> {
            UsuarioDocument doc = mongoMap.get(prof.getCpf());
            String status = (doc != null && doc.getPerfilProfessor() != null) ? "INTEGRADO_NOSQL" : "ASSINCRONO";

            String nomeDocente = (prof.getUsuario() != null) ? prof.getUsuario().getNome() : "Professor Sem Nome";
            if (doc != null && doc.getNome() != null) {
                nomeDocente = doc.getNome();
            }

            return new ProfessorDTO.Response(
                    prof.getCpf(),
                    prof.getMatricula(),
                    prof.getDepartamento(),
                    prof.getFormacao(),
                    prof.getJornada(),
                    prof.getSalario() != null ? prof.getSalario().doubleValue() : null,
                    prof.getDataAdmissao(),
                    status,
                    nomeDocente
            );
        });
    }

    @Transactional(readOnly = true)
    public Page<ProfessorDTO.Response> findAllNoSql(Pageable pageable) {
        Page<UsuarioDocument> usuariosDoc = usuarioNoSqlRepository.findByPerfilProfessorIsNotNull(pageable);

        List<ProfessorDTO.Response> professores = usuariosDoc.getContent().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(professores, pageable, usuariosDoc.getTotalElements());
    }

    @Transactional
    public ProfessorDTO.Response update(Long cpf, ProfessorDTO.Request dto) {
        Professor prof = relationalRepository.findById(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com o CPF: " + cpf));

        mapper.updateEntityFromDto(dto, prof);
        prof = relationalRepository.save(prof);

        eventPublisher.publishEvent(new ProfessorSalvoEvent(prof.getCpf(), dto));
        return mapper.toResponse(prof, "ASSINCRONO");
    }

    @Transactional
    public void delete(Long cpf) {
        if (!relationalRepository.existsById(cpf)) {
            throw new ResourceNotFoundException("Professor não encontrado com o CPF: " + cpf);
        }
        relationalRepository.deleteById(cpf);
        eventPublisher.publishEvent(new ProfessorDeletadoEvent(cpf));
    }

}