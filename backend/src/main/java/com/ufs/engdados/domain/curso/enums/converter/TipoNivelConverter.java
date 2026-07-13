package com.ufs.engdados.domain.curso.enums.converter;

import com.ufs.engdados.domain.curso.enums.TipoNivel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoNivelConverter implements AttributeConverter<TipoNivel, String> {

    @Override
    public String convertToDatabaseColumn(TipoNivel attribute) {
        return attribute == null ? null : attribute.getDescricao();
    }

    @Override
    public TipoNivel convertToEntityAttribute(String dbData) {
        return TipoNivel.deString(dbData);
    }
}
