package com.ufs.engdados.domain.vinculo.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate; // 🌟 Importado para os controles temporais de datas

@Document(collection = "vinculo")
@CompoundIndexes({
        @CompoundIndex(name = "idx_vinculo_estudante_curso", def = "{'matEstudante': 1, 'codCurso': 1}", unique = false)
})
public class VinculoDocument {

    @Id
    private String id;

    private Long idRelacional;
    private String matEstudante;
    private Long codCurso;
    private LocalDate dataEntrada;
    private String status;
    private LocalDate dataSaida;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getIdRelacional() { return idRelacional; }
    public void setIdRelacional(Long idRelacional) { this.idRelacional = idRelacional; }

    public String getMatEstudante() { return matEstudante; }
    public void setMatEstudante(String matEstudante) { this.matEstudante = matEstudante; }

    public Long getCodCurso() { return codCurso; }
    public void setCodCurso(Long codCurso) { this.codCurso = codCurso; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }
}