package com.ufs.engdados.domain.curso.enums.converter;

import com.ufs.engdados.domain.curso.enums.TipoTurno;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoTurnoConverter implements AttributeConverter<TipoTurno, String> {

    @Override
    public String convertToDatabaseColumn(TipoTurno attribute) {
        return attribute == null ? null : attribute.getDescricao();
    }

    @Override
    public TipoTurno convertToEntityAttribute(String dbData) {
        return TipoTurno.deString(dbData);
    }
}
