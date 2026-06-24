package com.ufs.engdados.domain.departamento.controller;

import com.ufs.engdados.domain.departamento.dto.DepartamentoDTO;
import com.ufs.engdados.domain.departamento.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/departamentos")
public class DepartamentoController{

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService){
        this.departamentoService = departamentoService;
    }

    @PostMapping
    public ResponseEntity<DepartamentoDTO.Response> create(@Valid @RequestBody DepartamentoDTO.Request request){
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoService.create(request));
    }
    @GetMapping("/relacional")
    public ResponseEntity<Page<DepartamentoDTO.Response>> findAllRelational(Pageable pageable){
        return ResponseEntity.ok(departamentoService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<DepartamentoDTO.Response>> findAllNoSql(Pageable pageable){
        return ResponseEntity.ok(departamentoService.findAllNoSql(pageable));
    }

    @PutMapping("/{codDepto}")
    public ResponseEntity<DepartamentoDTO.Response> update(@PathVariable String codDepto, @Valid @RequestBody DepartamentoDTO.Request request){
        return ResponseEntity.ok(departamentoService.update(codDepto, request));
    }

    @DeleteMapping("/{codDepto}")
    public ResponseEntity<Void> delete(@PathVariable String codDepto){
        departamentoService.delete(codDepto);
        return ResponseEntity.noContent().build();
    }

}