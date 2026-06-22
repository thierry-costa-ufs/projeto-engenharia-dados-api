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

    // CORREÇÃO: Adicionado Pageable e extração correta de conteúdo para retornar List
    @GetMapping
    public ResponseEntity<List<ProfessorDTO.Response>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(professorService.listarTodosRelacional(pageable).getContent());
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<ProfessorDTO.Response>> listarTodosRelacional(Pageable pageable) {
        return ResponseEntity.ok(professorService.listarTodosRelacional(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<ProfessorDTO.Response>> listarTodosNoSql(Pageable pageable) {
        Page<ProfessorDTO.Response> page = professorService.listarTodosNoSql(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO.Response> cadastrar(@Valid @RequestBody ProfessorDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.criar(dto));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO.Response> atualizar(@PathVariable Long cpf, @Valid @RequestBody ProfessorDTO.Request dto) {
        return ResponseEntity.ok(professorService.atualizar(cpf, dto));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> remover(@PathVariable Long cpf) {
        professorService.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}