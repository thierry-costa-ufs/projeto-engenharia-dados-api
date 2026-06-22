package com.ufs.engdados.domain.vinculo.model.relational;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vinculo", schema = "universidade")
public class Vinculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvinculo")
    private Long idVinculo;

    @Column(name = "mat_estudante")
    private String matEstudante;

    @Column(name = "curso")
    private Integer codCurso;

    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    @Column(name = "status")
    private String status;

    @Column(name = "data_saida")
    private LocalDate dataSaida;

    // Getters e Setters
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