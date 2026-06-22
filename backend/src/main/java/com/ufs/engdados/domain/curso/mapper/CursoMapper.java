package com.ufs.engdados.domain.curso.mapper;

import com.ufs.engdados.domain.curso.dto.CursoDTO;
import com.ufs.engdados.domain.curso.enums.TipoGrau;
import com.ufs.engdados.domain.curso.enums.TipoNivel;
import com.ufs.engdados.domain.curso.enums.TipoTurno;
import com.ufs.engdados.domain.curso.model.nosql.CursoDocument;
import com.ufs.engdados.domain.curso.model.relational.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    public Curso toEntity(CursoDTO.Request dto) {
        if (dto == null) return null;
        Curso ent = new Curso();
        ent.setNome(dto.nome());
        ent.setGrau(dto.grau());
        ent.setTurno(dto.turno());
        ent.setCampus(dto.campus());
        ent.setNivel(dto.nivel());
        return ent;
    }

    public void updateEntityFromDto(CursoDTO.Request dto, Curso ent) {
        if (dto == null || ent == null) return;
        ent.setNome(dto.nome());
        ent.setGrau(dto.grau());
        ent.setTurno(dto.turno());
        ent.setCampus(dto.campus());
        ent.setNivel(dto.nivel());
    }

    public CursoDTO.Response toResponse(Curso r, String statusExecucao) {
        if (r == null) return null;
        return new CursoDTO.Response(
                r.getIdCurso(),
                r.getNome(),
                TipoGrau.deString(r.getGrau()),
                TipoTurno.deString(r.getTurno()),
                r.getCampus(),
                TipoNivel.deString(r.getNivel()),
                statusExecucao
        );
    }

    public CursoDTO.Response toResponse(Curso r) {
        return toResponse(r, "ASSINCRONO");
    }

    public CursoDTO.Response fromMongoDocument(CursoDocument n) {
        if (n == null) return null;
        return new CursoDTO.Response(
                n.getIdCurso(),
                n.getNome(),
                n.getGrau(),
                n.getTurno(),
                n.getCampus(),
                n.getNivel(),
                "INTEGRADO_NOSQL"
        );
    }
}