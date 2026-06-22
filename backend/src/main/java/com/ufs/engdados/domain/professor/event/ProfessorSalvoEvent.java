package com.ufs.engdados.domain.professor.event;

import com.ufs.engdados.domain.professor.dto.ProfessorDTO;

public record ProfessorSalvoEvent(
        Long cpf,
        ProfessorDTO.Request dtoOriginal
) {}