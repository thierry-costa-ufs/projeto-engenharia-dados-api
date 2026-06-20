package com.ufs.engdados.domain.vinculo.model.relational;

import jakarta.persistence.*;

@Entity
@Table(name = "vinculo", schema = "universidade")
public class Vinculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvinculo")
    private Long idVinculo;

    @Column(name = "mat_estudante", nullable = false, length = 7)
    private String matEstudante;

    @Column(name = "curso", nullable = false)
    private Integer codCurso;

    @Column(name = "status")
    private String situacao;

    // --- GETTERS E SETTERS ---

    public Long getIdVinculo() {
        return idVinculo;
    }

    public void setIdVinculo(Long idVinculo) {
        this.idVinculo = idVinculo;
    }

    public String getMatEstudante() {
        return matEstudante;
    }

    public void setMatEstudante(String matEstudante) {
        this.matEstudante = matEstudante;
    }

    public Integer getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(Integer codCurso) {
        this.codCurso = codCurso;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}