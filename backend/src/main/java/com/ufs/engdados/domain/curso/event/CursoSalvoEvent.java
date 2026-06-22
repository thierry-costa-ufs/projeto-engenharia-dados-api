package com.ufs.engdados.domain.curso.event;

import com.ufs.engdados.domain.curso.dto.CursoDTO;

public record CursoSalvoEvent(
        Integer idCurso,
        CursoDTO.Request dtoOriginal
) {}