package com.ufs.engdados.domain.curso.controller;

import com.ufs.engdados.domain.curso.dto.CursoDTO;
import com.ufs.engdados.domain.curso.service.CursoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoDTO.Response> cadastrar(@Valid @RequestBody CursoDTO.Request dto) {
        CursoDTO.Response novoCurso = cursoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<CursoDTO.Response>> listarRelacional(Pageable pageable) {
        return ResponseEntity.ok(cursoService.listarTodosRelacional(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<CursoDTO.Response>> listarNoSql(Pageable pageable) {
        return ResponseEntity.ok(cursoService.listarTodosNoSql(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO.Response> atualizar(@PathVariable Integer id, @Valid @RequestBody CursoDTO.Request dto) {
        CursoDTO.Response cursoAtualizado = cursoService.atualizar(id, dto);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}