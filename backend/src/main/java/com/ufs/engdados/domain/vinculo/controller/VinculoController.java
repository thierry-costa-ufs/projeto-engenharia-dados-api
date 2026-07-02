package com.ufs.engdados.domain.vinculo.controller;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.service.VinculoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;

    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @PostMapping
    public ResponseEntity<VinculoDTO.Response> create(@RequestBody VinculoDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vinculoService.create(dto));
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<VinculoDTO.Response>> findAllRelational(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(vinculoService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<VinculoDTO.Response>> findAllNoSql(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(vinculoService.findAllNoSql(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VinculoDTO.Response> update(@PathVariable Long id, @RequestBody VinculoDTO.Request dto) {
        return ResponseEntity.ok(vinculoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vinculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}