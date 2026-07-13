package com.ufs.engdados.domain.professor.enums.converter;

import com.ufs.engdados.domain.professor.enums.TipoFormacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoFormacaoConverter implements AttributeConverter<TipoFormacao, String> {

    @Override
    public String convertToDatabaseColumn(TipoFormacao atributo) {
        if (atributo == null) return null;
        return atributo.getCodigoDb(); // Converte para o texto do Postgres (ex: "Graduação")
    }

    @Override
    public TipoFormacao convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return TipoFormacao.deString(dbData); // Lê do Postgres e vira Enum
    }
}