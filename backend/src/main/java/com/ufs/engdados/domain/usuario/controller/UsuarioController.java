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
    public ResponseEntity<UsuarioDTO.Response> create(@Valid @RequestBody UsuarioDTO.Request dto) {
        UsuarioDTO.Response novoUsuario = usuarioService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/relacional")
<<<<<<< Updated upstream
    public ResponseEntity<Page<UsuarioDTO.Response>> findAllRelational(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<UsuarioDTO.Response>> findAllNoSql(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAllNoSql(pageable));
=======
    public ResponseEntity<Page<UsuarioDTO.Response>> listRelational(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<UsuarioDTO.Response>> listNoSql(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listNoSql(pageable));
>>>>>>> Stashed changes
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<UsuarioDTO.Response> update(@PathVariable Long cpf, @Valid @RequestBody UsuarioDTO.Request dto) {
        UsuarioDTO.Response usuarioAtualizado = usuarioService.update(cpf, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{cpf}")
<<<<<<< Updated upstream
    public ResponseEntity<Void> delete(@PathVariable Long cpf) {
=======
    public ResponseEntity<Void> remove(@PathVariable Long cpf) {
>>>>>>> Stashed changes
        usuarioService.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}