package com.ufs.engdados.domain.usuario.controller;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO.Response> create(@Valid @RequestBody UsuarioDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(dto));
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<UsuarioDTO.Response>> findAllRelational(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<UsuarioDTO.Response>> findAllNoSql(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAllNoSql(pageable));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<UsuarioDTO.Response> update(@PathVariable Long cpf, @Valid @RequestBody UsuarioDTO.Request dto) {
        return ResponseEntity.ok(usuarioService.update(cpf, dto));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable Long cpf) {
        usuarioService.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}