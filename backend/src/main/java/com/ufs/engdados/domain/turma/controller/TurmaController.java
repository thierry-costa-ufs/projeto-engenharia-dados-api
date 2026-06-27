package com.ufs.engdados.domain.turma.controller;

import com.ufs.engdados.domain.turma.dto.TurmaDTO;
import com.ufs.engdados.domain.turma.service.TurmaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas") // Caminho atualizado
@CrossOrigin(origins = "*") // Libera o React
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> criarTurma(@RequestBody TurmaDTO dto) {
        service.salvarTurma(dto);
        // Devolve o DTO salvo com status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // rotas de Leitura
    @GetMapping("/relacional")
    public ResponseEntity<List<TurmaDTO>> listarTodasRelacional() {
        return ResponseEntity.ok(service.listarTodasRelacional());
    }

    @GetMapping("/nosql")
    public ResponseEntity<List<TurmaDTO>> listarTodasNoSql() {
        return ResponseEntity.ok(service.listarTodasNoSql());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> atualizarTurma(
            @PathVariable("id") Integer id,
            @RequestBody TurmaDTO dto) {

        service.atualizarTurma(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable("id") Integer id) {
        service.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }
}