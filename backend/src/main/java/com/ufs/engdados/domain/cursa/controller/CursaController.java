package com.ufs.engdados.domain.cursa.controller;

import com.ufs.engdados.domain.cursa.dto.CursaDTO;
import com.ufs.engdados.domain.cursa.service.CursaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cursa")
@CrossOrigin(origins = "*") // libera o React
public class CursaController {

    private final CursaService service;

    public CursaController(CursaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CursaDTO.Response> criarCursa(@Valid @RequestBody CursaDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<CursaDTO.Response>> listarTodasRelacional(@PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(service.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<CursaDTO.Response>> listarTodasNoSql(@PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(service.findAllNoSql(pageable));
    }

    @PutMapping("/{matEstudante}/{idTurma}")
    public ResponseEntity<CursaDTO.Response> atualizarCursa(
            @PathVariable("matEstudante") String matEstudante,
            @PathVariable("idTurma") Integer idTurma,
            @Valid @RequestBody CursaDTO.Request request) {

        return ResponseEntity.ok(service.update(matEstudante, idTurma, request));
    }

    @DeleteMapping("/{matEstudante}/{idTurma}")
    public ResponseEntity<Void> deletarCursa(
            @PathVariable("matEstudante") String matEstudante,
            @PathVariable("idTurma") Integer idTurma) {

        service.delete(matEstudante, idTurma);
        return ResponseEntity.noContent().build();
    }
}