package com.ufs.engdados.domain.departamento.model.relational;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "departamento", schema = "universidade")
public class Departamento{

    @Id
    private String cod_depto;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "chefe", length =  7)
    private String chefe;

    @Column(name = "orcamento")
    private BigDecimal orcamento;

    @Column(name = "comissal")
    private BigDecimal comissal;

    // --- GETTERS E SETTERS ---

    public String getChefe() {
        return chefe;
    }
    public void setChefe(String chefe) {
        this.chefe = chefe;
    }

    public String getCodDepto() {
        return cod_depto;
    }
    public void setCodDepto(String cod_depto) {
        this.cod_depto = cod_depto;
    }

    public BigDecimal getComissal() {
        return comissal;
    }
    public void setComissal(BigDecimal comissal) {
        this.comissal = comissal;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }
    public void setOrcamento(BigDecimal orcamento) {
        this.orcamento = orcamento;
    }

}