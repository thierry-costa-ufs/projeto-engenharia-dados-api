package com.ufs.engdados.domain.vinculo.mapper;

import com.ufs.engdados.domain.vinculo.dto.VinculoDTO;
import com.ufs.engdados.domain.vinculo.model.nosql.VinculoDocument;
import com.ufs.engdados.domain.vinculo.model.relational.Vinculo;

public class VinculoMapper {

    public static Vinculo toEntity(VinculoDTO.Request dto) {
        if(dto == null) return null;
        Vinculo vinculo = new Vinculo();
        vinculo.setIdVinculo(dto.idVinculo());
        vinculo.setMatEstudante(dto.matEstudante());
        vinculo.setCodCurso(dto.codCurso());
        vinculo.setDataEntrada(dto.dataEntrada());
        vinculo.setStatus(dto.status());
        vinculo.setDataSaida(dto.dataSaida());
        return vinculo;
    }

    public static VinculoDocument toDocument(VinculoDTO.Request dto) {
        if(dto == null) return null;
        VinculoDocument doc = new VinculoDocument();
        doc.setIdVinculo(dto.idVinculo());
        doc.setMatEstudante(dto.matEstudante());
        doc.setCodCurso(dto.codCurso());
        doc.setDataEntrada(dto.dataEntrada());
        doc.setStatus(dto.status());
        doc.setDataSaida(dto.dataSaida());
        return doc;
    }

    public static VinculoDTO.Response toResponse(Vinculo v) {
        return new VinculoDTO.Response(
                null,
                v.getIdVinculo(),
                v.getMatEstudante(),
                v.getCodCurso(),
                v.getDataEntrada(),
                v.getStatus(),
                v.getDataSaida()
        );
    }

    public static VinculoDTO.Response toResponse(VinculoDocument vinculo) {
        return new VinculoDTO.Response(
                vinculo.getId(),
                vinculo.getIdVinculo(),
                vinculo.getMatEstudante(),
                vinculo.getCodCurso(),
                vinculo.getDataEntrada(),
                vinculo.getStatus(),
                vinculo.getDataSaida()
        );
    }

    public static void updateEntity(VinculoDTO.Request dto, Vinculo vinculo){
        if(dto == null || vinculo == null) return;
        vinculo.setIdVinculo(dto.idVinculo());
        vinculo.setMatEstudante(dto.matEstudante());
        vinculo.setCodCurso(dto.codCurso());
        vinculo.setDataEntrada(dto.dataEntrada());
        vinculo.setStatus(dto.status());
        vinculo.setDataSaida(dto.dataSaida());
    }

    public static void updateDocument(VinculoDTO.Request dto, VinculoDocument vinculo){
        if(dto == null || vinculo == null) return;
        vinculo.setIdVinculo(dto.idVinculo());
        vinculo.setMatEstudante(dto.matEstudante());
        vinculo.setCodCurso(dto.codCurso());
        vinculo.setDataEntrada(dto.dataEntrada());
        vinculo.setStatus(dto.status());
        vinculo.setDataSaida(dto.dataSaida());
    }

}