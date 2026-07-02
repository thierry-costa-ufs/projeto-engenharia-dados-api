package com.ufs.engdados.domain.turma.model.relational;

import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import jakarta.persistence.*;

@Entity
@Table(name = "turma", schema = "universidade")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_turma_gen")
    @SequenceGenerator(name = "seq_turma_gen", sequenceName = "universidade.seq_turma", allocationSize = 1)
    @Column(name = "id_turma")
    private Integer idTurma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_disc", nullable = false)
    private Disciplina disciplina;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "ano")
    private Short ano;

    @Column(name = "semestre")
    private Short semestre;

    public Turma() {
    }

    // getters e setters

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Short getAno() {
        return ano;
    }

    public void setAno(Short ano) {
        this.ano = ano;
    }

    public Short getSemestre() {
        return semestre;
    }

    public void setSemestre(Short semestre) {
        this.semestre = semestre;
    }
}