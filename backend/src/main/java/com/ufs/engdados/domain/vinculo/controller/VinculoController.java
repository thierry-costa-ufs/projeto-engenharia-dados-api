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
    public ResponseEntity<VinculoDTO.Response> criar(@RequestBody VinculoDTO.Request dto) {
        VinculoDTO.Response response = vinculoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<VinculoDTO.Response>> listarRelacional(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(vinculoService.listarTodosRelacional(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<VinculoDTO.Response>> listarNoSql(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(vinculoService.listarTodosNoSql(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VinculoDTO.Response> atualizar(@PathVariable Long id, @RequestBody VinculoDTO.Request dto) {
        return ResponseEntity.ok(vinculoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vinculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}