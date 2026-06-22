package com.ufs.engdados.domain.professor.controller;

import com.ufs.engdados.domain.professor.dto.ProfessorDTO;
import com.ufs.engdados.domain.professor.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO.Response> create(@Valid @RequestBody ProfessorDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO.Response>> findAll(Pageable pageable) {
        return ResponseEntity.ok(professorService.findAllRelational(pageable).getContent());
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<ProfessorDTO.Response>> findAllRelational(Pageable pageable) {
        return ResponseEntity.ok(professorService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<ProfessorDTO.Response>> findAllNoSql(Pageable pageable) {
        Page<ProfessorDTO.Response> page = professorService.findAllNoSql(pageable);
        return ResponseEntity.ok(page);
    }


    @PutMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO.Response> update(@PathVariable Long cpf, @Valid @RequestBody ProfessorDTO.Request dto) {
        return ResponseEntity.ok(professorService.update(cpf, dto));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable Long cpf) {
        professorService.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}