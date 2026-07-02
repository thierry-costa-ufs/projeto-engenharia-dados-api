package com.ufs.engdados.domain.curso.mapper;

import com.ufs.engdados.domain.curso.dto.CursoDTO;
import com.ufs.engdados.domain.curso.model.nosql.CursoDocument;
import com.ufs.engdados.domain.curso.model.relational.Curso;

public class CursoMapper {

    public static Curso toEntity(CursoDTO.Request dto) {
        if (dto == null) return null;
        Curso ent = new Curso();
        ent.setIdCurso(dto.idCurso());
        ent.setNome(dto.nome());
        ent.setGrau(dto.grau());
        ent.setTurno(dto.turno());
        ent.setCampus(dto.campus());
        ent.setNivel(dto.nivel());
        return ent;
    }

    public static CursoDocument toDocument(CursoDTO.Request dto){
        if (dto == null) return null;
        CursoDocument ent = new CursoDocument();
        ent.setIdCurso(dto.idCurso());
        ent.setNome(dto.nome());
        ent.setGrau(dto.grau());
        ent.setTurno(dto.turno());
        ent.setCampus(dto.campus());
        ent.setNivel(dto.nivel());
        return ent;
    }

    public static CursoDTO.Response toResponse(Curso r) {
        if (r == null) return null;
        return new CursoDTO.Response(
                null,
                r.getIdCurso(),
                r.getNome(),
                r.getGrau(),
                r.getTurno(),
                r.getCampus(),
                r.getNivel()
        );
    }
    public static CursoDTO.Response toResponse(CursoDocument n) {
        if (n == null) return null;
        return new CursoDTO.Response(
                n.getId(),
                n.getIdCurso(),
                n.getNome(),
                n.getGrau(),
                n.getTurno(),
                n.getCampus(),
                n.getNivel()
        );
    }

    public static void updateEntity(CursoDTO.Request dto, Curso ent) {
        if (dto == null || ent == null) return;
        ent.setIdCurso(dto.idCurso());
        ent.setNome(dto.nome());
        ent.setGrau(dto.grau());
        ent.setTurno(dto.turno());
        ent.setCampus(dto.campus());
        ent.setNivel(dto.nivel());
    }

    public static void updateDocument(CursoDTO.Request dto, CursoDocument ent) {
        if (dto == null || ent == null) return;
        ent.setIdCurso(dto.idCurso());
        ent.setNome(dto.nome());
        ent.setGrau(dto.grau());
        ent.setTurno(dto.turno());
        ent.setCampus(dto.campus());
        ent.setNivel(dto.nivel());
    }
}