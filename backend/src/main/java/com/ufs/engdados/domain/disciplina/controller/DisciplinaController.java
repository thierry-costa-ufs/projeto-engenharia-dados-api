package com.ufs.engdados.domain.disciplina.controller;

import com.ufs.engdados.domain.disciplina.dto.DisciplinaDTO;
import com.ufs.engdados.domain.disciplina.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/disciplinas") // caminho base atualizado
@CrossOrigin(origins = "*") // libera o acesso para o React
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO> criarDisciplina(@RequestBody DisciplinaDTO dto) {
        service.salvarDisciplina(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/relacional") // rota para o botão PostgreSQL
    public ResponseEntity<List<DisciplinaDTO>> listarTodasRelacional() {
        return ResponseEntity.ok(service.listarTodasRelacional());
    }

    @GetMapping("/nosql") // rota para o botão MongoDB
    public ResponseEntity<List<DisciplinaDTO>> listarTodasNoSql() {
        return ResponseEntity.ok(service.listarTodasNoSql());
    }

    @PutMapping("/{codDisc}")
    public ResponseEntity<DisciplinaDTO> atualizarDisciplina(
            @PathVariable("codDisc") String codDisc,
            @RequestBody DisciplinaDTO dto) {

        service.atualizarDisciplina(codDisc, dto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{codDisc}")
    public ResponseEntity<Void> deletarDisciplina(
            @PathVariable("codDisc") String codDisc) { // <-- ADICIONAMOS ("codDisc") AQUI
        service.deletarDisciplina(codDisc);
        return ResponseEntity.noContent().build();
    }
}