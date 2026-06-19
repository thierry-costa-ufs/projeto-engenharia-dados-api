package com.ufs.engdados.controller;

import com.ufs.engdados.dto.UsuarioDTO;
import com.ufs.engdados.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*") // Permite a comunicação com o React localmente
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO dto) {
        UsuarioDTO novoUsuario = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/relacional")
    public ResponseEntity<List<UsuarioDTO>> listarRelacional() {
        return ResponseEntity.ok(usuarioService.listarTodosRelacional());
    }

    @GetMapping("/nosql")
    public ResponseEntity<List<UsuarioDTO>> listarNoSql() {
        return ResponseEntity.ok(usuarioService.listarTodosNoSql());
    }
}