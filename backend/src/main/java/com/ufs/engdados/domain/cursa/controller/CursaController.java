package com.ufs.engdados.domain.cursa.controller;

import com.ufs.engdados.domain.cursa.dto.CursaDTO;
import com.ufs.engdados.domain.cursa.service.CursaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursa")
@CrossOrigin(origins = "*") // libera o React
public class CursaController {

    private final CursaService service;

    public CursaController(CursaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CursaDTO> criarCursa(@RequestBody CursaDTO dto) {
        service.salvarCursa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // rotas de Leitura
    @GetMapping("/relacional")
    public ResponseEntity<List<CursaDTO>> listarTodasRelacional() {
        return ResponseEntity.ok(service.listarTodasRelacional());
    }

    @GetMapping("/nosql")
    public ResponseEntity<List<CursaDTO>> listarTodasNoSql() {
        return ResponseEntity.ok(service.listarTodasNoSql());
    }

    @PutMapping("/{matEstudante}/{idTurma}")
    public ResponseEntity<CursaDTO> atualizarCursa(
            @PathVariable("matEstudante") String matEstudante,
            @PathVariable("idTurma") Integer idTurma,
            @RequestBody CursaDTO dto) {

        service.atualizarCursa(matEstudante, idTurma, dto);
        return ResponseEntity.ok(dto); // Devolve o DTO
    }

    @DeleteMapping("/{matEstudante}/{idTurma}")
    public ResponseEntity<Void> deletarCursa(
            @PathVariable("matEstudante") String matEstudante,
            @PathVariable("idTurma") Integer idTurma) {

        service.deletarCursa(matEstudante, idTurma);
        return ResponseEntity.noContent().build();
    }
}