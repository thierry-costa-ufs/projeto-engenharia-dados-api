package com.ufs.engdados.domain.curso.enums.converter;

import com.ufs.engdados.domain.curso.enums.TipoGrau;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoGrauConverter implements AttributeConverter<TipoGrau, String> {

    @Override
    public String convertToDatabaseColumn(TipoGrau attribute) {
        return attribute == null ? null : attribute.getDescricao();
    }

    @Override
    public TipoGrau convertToEntityAttribute(String dbData) {
        return TipoGrau.deString(dbData);
    }
}
