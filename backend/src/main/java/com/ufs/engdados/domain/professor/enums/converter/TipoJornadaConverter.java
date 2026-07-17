package com.ufs.engdados.domain.professor.enums.converter;
import com.ufs.engdados.domain.professor.enums.TipoJornada;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoJornadaConverter implements AttributeConverter<TipoJornada, String> {

    @Override
    public String convertToDatabaseColumn(TipoJornada atributo) {
        if (atributo == null) return null;
        return atributo.getCodigoDb(); // Converte para "20h", "40h", "DE"
    }

    @Override
    public TipoJornada convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return TipoJornada.deString(dbData);
    }
}