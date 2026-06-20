package com.ufs.engdados.domain.usuario.controller;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO.Response> cadastrar(@RequestBody UsuarioDTO.Request dto) {
        UsuarioDTO.Response novoUsuario = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/relacional")
    public ResponseEntity<List<UsuarioDTO.Response>> listarRelacional() {
        return ResponseEntity.ok(usuarioService.listarTodosRelacional());
    }

    @GetMapping("/nosql")
    public ResponseEntity<List<UsuarioDTO.Response>> listarNoSql() {
        return ResponseEntity.ok(usuarioService.listarTodosNoSql());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<UsuarioDTO.Response> atualizar(@PathVariable Long cpf, @RequestBody UsuarioDTO.Request dto) {
        UsuarioDTO.Response usuarioAtualizado = usuarioService.atualizar(cpf, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> remover(@PathVariable Long cpf) {
        usuarioService.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}