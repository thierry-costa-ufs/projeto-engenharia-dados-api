package com.ufs.engdados.domain.vinculo.mapper;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;

public class VinculoMapper {

    public static Vinculo toPostgresEntity(VinculoDTO.Request dto) {
        Vinculo vinculo = new Vinculo();
        vinculo.setMatEstudante(dto.matEstudante());
        vinculo.setCodCurso(dto.codCurso() != null ? dto.codCurso().intValue() : null);

        vinculo.setStatus(dto.situacao());
        return vinculo;
    }

    public static VinculoDocument toMongoDocument(VinculoDTO.Request dto) {
        VinculoDocument doc = new VinculoDocument();
        doc.setMatEstudante(dto.matEstudante());
        doc.setCodCurso(dto.codCurso());
        doc.setSemestre(dto.semestre());
        doc.setSituacao(dto.situacao());
        return doc;
    }

    public static VinculoDTO.Response toResponse(Vinculo vinculo, String mongoId, String statusExecucao) {
        Long cursoLong = (vinculo.getCodCurso() != null) ? Long.valueOf(vinculo.getCodCurso().longValue()) : null;

        return new VinculoDTO.Response(
                vinculo.getIdVinculo(),
                mongoId,
                vinculo.getMatEstudante(),
                cursoLong,
                null,
                vinculo.getStatus(),
                statusExecucao
        );
    }

    public static VinculoDTO.Response fromPostgresEntity(Vinculo v) {
        Long cursoLong = (v.getCodCurso() != null) ? Long.valueOf(v.getCodCurso().longValue()) : null;

        return new VinculoDTO.Response(
                v.getIdVinculo(),
                null,
                v.getMatEstudante(),
                cursoLong,
                null,
                v.getStatus(),
                "SUCESSO_POSTGRES"
        );
    }

    public static VinculoDTO.Response fromMongoDocument(VinculoDocument doc) {
        Long cursoLong = (doc.getCodCurso() != null) ? Long.valueOf(doc.getCodCurso().longValue()) : null;

        return new VinculoDTO.Response(
                null,
                doc.getId(),
                doc.getMatEstudante(),
                cursoLong,
                doc.getSemestre(),
                doc.getSituacao(), // Mapeia o campo do mongo para a mesma posição do DTO
                "SUCESSO_MONGO"
        );
    }
}