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
    public ResponseEntity<CursoDTO.Response> create(@Valid @RequestBody CursoDTO.Request dto) {
        CursoDTO.Response novoCurso = cursoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<CursoDTO.Response>> findAllRelational(Pageable pageable) {
        return ResponseEntity.ok(cursoService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<CursoDTO.Response>> findAllNoSql(Pageable pageable) {
        return ResponseEntity.ok(cursoService.findAllNoSql(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO.Response> update(@PathVariable Integer id, @Valid @RequestBody CursoDTO.Request dto) {
        CursoDTO.Response cursoAtualizado = cursoService.update(id, dto);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}