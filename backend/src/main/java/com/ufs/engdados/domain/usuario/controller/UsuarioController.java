package com.ufs.engdados.domain.usuario.controller;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<UsuarioDTO.Response> cadastrar(@Valid @RequestBody UsuarioDTO.Request dto) {
        UsuarioDTO.Response novoUsuario = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<UsuarioDTO.Response>> listarRelacional(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listarTodosRelacional(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<UsuarioDTO.Response>> listarNoSql(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listarTodosNoSql(pageable));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<UsuarioDTO.Response> atualizar(@PathVariable Long cpf, @Valid @RequestBody UsuarioDTO.Request dto) {
        UsuarioDTO.Response usuarioAtualizado = usuarioService.atualizar(cpf, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> remover(@PathVariable Long cpf) {
        usuarioService.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}