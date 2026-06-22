package com.ufs.engdados.domain.estudante.controller;

import com.ufs.engdados.domain.estudante.dto.EstudanteDTO;
import com.ufs.engdados.domain.estudante.service.EstudanteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/v1/estudantes")
public class EstudanteController{

    private final EstudanteService estudanteService;

    public EstudanteController(EstudanteService estudanteServie){
        this.estudanteService = estudanteServie;
    }

    @PostMapping
    public ResponseEntity<EstudanteDTO.Response> create(@Valid @RequestBody EstudanteDTO.Request request){
        EstudanteDTO.Response resultado = estudanteService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/relacional")
    public ResponseEntity<Page<EstudanteDTO.Response>> listRelational(Pageable pageable){
        return ResponseEntity.ok(estudanteService.listRelational(pageable));
    }

    @GetMapping("/nosql")
    public ResponseEntity<Page<EstudanteDTO.Response>> listNoSql(Pageable pageable){
        return ResponseEntity.ok(estudanteService.listNoSql(pageable));
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<EstudanteDTO.Response> update(@PathVariable String matricula, @Valid @RequestBody EstudanteDTO.Request request){
        EstudanteDTO.Response resultado = estudanteService.update(matricula, request);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> delete(@PathVariable String matricula){
        estudanteService.delete(matricula);
        return ResponseEntity.noContent().build();
    }


}