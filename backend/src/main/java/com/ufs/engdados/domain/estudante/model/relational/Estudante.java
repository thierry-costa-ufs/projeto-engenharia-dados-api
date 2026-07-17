package com.ufs.engdados.domain.estudante.model.relational;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudante", schema = "universidade")
public class Estudante{

    @Id @Column(name = "mat_estudante", length = 7, nullable = false)
    private String matEstudante;

    @Column(name = "cpf")
    private Long cpf;

    @Column(name = "mc")
    private Double mc;

    @Column(name = "ano_ingresso")
    private Integer anoIngresso;

    // --- GETTERS E SETTERS ---

    public Integer getAnoIngresso() {
        return anoIngresso;
    }
    public void setAnoIngresso(Integer anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public long getCpf() {
        return cpf;
    }
    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getMatEstudante() {
        return matEstudante;
    }
    public void setMatEstudante(String matEstudante) {
        this.matEstudante = matEstudante;
    }

    public Double getMc() {
        return mc;
    }
    public void setMc(Double mc) {
        this.mc = mc;
    }
}