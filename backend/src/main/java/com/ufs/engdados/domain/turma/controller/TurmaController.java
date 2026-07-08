package com.ufs.engdados.domain.turma.controller;

import com.ufs.engdados.domain.turma.dto.TurmaDTO;
import com.ufs.engdados.domain.turma.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/turmas")
@CrossOrigin(origins = "*") // Libera o React
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    public ResponseEntity<TurmaDTO.Response> create(@Valid @RequestBody TurmaDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaService.create(request));
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<TurmaDTO.Response>> findAllRelational(@PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(turmaService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<TurmaDTO.Response>> findAllNoSql(@PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(turmaService.findAllNoSql(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO.Response> update(@PathVariable("id") Integer id, @Valid @RequestBody TurmaDTO.Request request) {
        return ResponseEntity.ok(turmaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        turmaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}