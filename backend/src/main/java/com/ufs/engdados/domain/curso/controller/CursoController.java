package com.ufs.engdados.domain.curso.controller;

import com.ufs.engdados.domain.curso.dto.CursoDTO;
import com.ufs.engdados.domain.curso.service.CursoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.create(dto));
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<CursoDTO.Response>> findAllRelational(@PageableDefault(size = 20)Pageable pageable) {
        return ResponseEntity.ok(cursoService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<CursoDTO.Response>> findAllNoSql(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(cursoService.findAllNoSql(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO.Response> update(@PathVariable Integer id, @Valid @RequestBody CursoDTO.Request dto) {
        return ResponseEntity.ok(cursoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}