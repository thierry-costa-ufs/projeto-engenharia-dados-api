package com.ufs.engdados.domain.vinculo.mapper;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;

public class VinculoMapper {

    public static Vinculo toPostgresEntity(VinculoDTO.Request dto) {
        Vinculo vinculo = new Vinculo();
        vinculo.setMatEstudante(dto.matEstudante());
        vinculo.setCodCurso(dto.codCurso() != null ? dto.codCurso().intValue() : null);
        // Removido o setSemestre que não existe na entidade
        vinculo.setSituacao(dto.situacao());
        return vinculo;
    }

    public static VinculoDocument toMongoDocument(VinculoDTO.Request dto) {
        VinculoDocument doc = new VinculoDocument();
        doc.setMatEstudante(dto.matEstudante());
        // CORRIGIDO: VinculoDocument espera Long, então passamos o Long do DTO diretamente
        doc.setCodCurso(dto.codCurso());
        doc.setSemestre(dto.semestre());
        doc.setSituacao(dto.situacao());
        return doc;
    }

    public static VinculoDTO.Response toResponse(Vinculo vinculo, String mongoId, String status) {
        Long cursoLong = (vinculo.getCodCurso() != null) ? Long.valueOf(vinculo.getCodCurso().longValue()) : null;

        return new VinculoDTO.Response(
                vinculo.getIdVinculo(),
                mongoId,
                vinculo.getMatEstudante(),
                cursoLong,
                null, // CORRIGIDO: Como o Postgres não possui semestre, passamos null ou tratamos de outra forma
                vinculo.getSituacao(),
                status
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
                v.getSituacao(),
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
                doc.getSituacao(),
                "SUCESSO_MONGO"
        );
    }
}