package com.ufs.engdados.domain.disciplina.controller;

import com.ufs.engdados.domain.disciplina.dto.DisciplinaDTO;
import com.ufs.engdados.domain.disciplina.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/disciplinas")
@CrossOrigin(origins = "*") // libera o acesso para o React
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO.Response> criarDisciplina(@Valid @RequestBody DisciplinaDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<DisciplinaDTO.Response>> listarTodasRelacional(@PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(service.findAllRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<DisciplinaDTO.Response>> listarTodasNoSql(@PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(service.findAllNoSql(pageable));
    }

    @PutMapping("/{codDisc}")
    public ResponseEntity<DisciplinaDTO.Response> atualizarDisciplina(
            @PathVariable("codDisc") String codDisc,
            @Valid @RequestBody DisciplinaDTO.Request request) {
        return ResponseEntity.ok(service.update(codDisc, request));
    }

    @DeleteMapping("/{codDisc}")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable("codDisc") String codDisc) {
        service.delete(codDisc);
        return ResponseEntity.noContent().build();
    }
}