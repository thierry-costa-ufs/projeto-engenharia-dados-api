package com.ufs.engdados.domain.vinculo.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate; // 🌟 Importado para os controles temporais de datas

@Document(collection = "vinculo")
@CompoundIndexes({
        @CompoundIndex(name = "idx_vinculo_estudante_curso", def = "{'matEstudante': 1, 'codCurso': 1}", unique = false)
})
public class VinculoDocument {

    @Id
    private String id;

    @Field
    private Long idVinculo;

    @Field
    private String matEstudante;

    @Field
    private Integer codCurso;

    @Field
    private LocalDate dataEntrada;

    @Field
    private String status;

    @Field
    private LocalDate dataSaida;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getIdVinculo() { return idVinculo; }
    public void setIdVinculo(Long idVinculo) { this.idVinculo = idVinculo; }

    public String getMatEstudante() { return matEstudante; }
    public void setMatEstudante(String matEstudante) { this.matEstudante = matEstudante; }

    public Integer getCodCurso() { return codCurso; }
    public void setCodCurso(Integer codCurso) { this.codCurso = codCurso; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }
}