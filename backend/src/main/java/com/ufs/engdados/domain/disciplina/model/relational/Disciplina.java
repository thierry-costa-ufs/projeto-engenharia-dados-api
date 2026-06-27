package com.ufs.engdados.domain.disciplina.model.relational;

import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import jakarta.persistence.*;

@Entity
@Table(name = "disciplina", schema = "universidade")
public class Disciplina {

    @Id
    @Column(name = "cod_disc", length = 8)
    private String codDisc;

    @Column(nullable = false, length = 40)
    private String nome;

    @Column(name = "creditos")
    private Integer creditos;

    // auto-relacionamento: uma disciplina tem outra disciplina como pré-requisito
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_req")
    private Disciplina preReq;

    // relacionamento com o Departamento responsável
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depto_responsavel")
    private Departamento deptoResponsavel;

    // construtor vazio exigido pelo JPA
    public Disciplina() {
    }

    // getters e setters

    public String getCodDisc() {
        return codDisc;
    }

    public void setCodDisc(String codDisc) {
        this.codDisc = codDisc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Disciplina getPreReq() {
        return preReq;
    }

    public void setPreReq(Disciplina preReq) {
        this.preReq = preReq;
    }

    public Departamento getDeptoResponsavel() {
        return deptoResponsavel;
    }

    public void setDeptoResponsavel(Departamento deptoResponsavel) {
        this.deptoResponsavel = deptoResponsavel;
    }
}