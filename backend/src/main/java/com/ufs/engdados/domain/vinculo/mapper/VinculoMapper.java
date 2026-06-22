package com.ufs.engdados.domain.vinculo.mapper;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;

public class VinculoMapper {

    public static Vinculo toEntity(VinculoDTO.Request dto) {
        Vinculo vinculo = new Vinculo();
        vinculo.setMatEstudante(dto.matEstudante());
        vinculo.setCodCurso(dto.codCurso() != null ? dto.codCurso().intValue() : null);

        vinculo.setDataEntrada(dto.dataEntrada());
        vinculo.setStatus(dto.status());
        vinculo.setDataSaida(dto.dataSaida());
        return vinculo;
    }

    public static VinculoDocument toDocument(VinculoDTO.Request dto) {
        VinculoDocument doc = new VinculoDocument();
        doc.setMatEstudante(dto.matEstudante());
        doc.setCodCurso(dto.codCurso());

        doc.setDataEntrada(dto.dataEntrada());
        doc.setStatus(dto.status());
        doc.setDataSaida(dto.dataSaida());
        return doc;
    }

    public static VinculoDTO.Response toResponse(Vinculo vinculo, String mongoId, String statusExecucao) {
        Long cursoLong = (vinculo.getCodCurso() != null) ? Long.valueOf(vinculo.getCodCurso().longValue()) : null;

        return new VinculoDTO.Response(
                vinculo.getIdVinculo(),
                mongoId,
                vinculo.getMatEstudante(),
                cursoLong,
                vinculo.getDataEntrada(),
                vinculo.getStatus(),
                vinculo.getDataSaida(),
                statusExecucao
        );
    }

    public static VinculoDTO.Response toResponse(Vinculo v) {
        Long cursoLong = (v.getCodCurso() != null) ? Long.valueOf(v.getCodCurso().longValue()) : null;

        return new VinculoDTO.Response(
                v.getIdVinculo(),
                null,
                v.getMatEstudante(),
                cursoLong,
                v.getDataEntrada(),
                v.getStatus(),
                v.getDataSaida(),
                "SUCESSO_POSTGRES"
        );
    }

    public static VinculoDTO.Response toResponse(VinculoDocument doc) {
        Long cursoLong = (doc.getCodCurso() != null) ? Long.valueOf(doc.getCodCurso().longValue()) : null;

        return new VinculoDTO.Response(
                doc.getIdRelacional(),
                doc.getId(),
                doc.getMatEstudante(),
                cursoLong,
                doc.getDataEntrada(),
                doc.getStatus(),
                doc.getDataSaida(),
                "SUCESSO_MONGO"
        );
    }
}