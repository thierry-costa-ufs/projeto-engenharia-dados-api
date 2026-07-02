package com.ufs.engdados.domain.cursa.model.relational;

import com.ufs.engdados.domain.estudante.model.relational.Estudante;
import com.ufs.engdados.domain.turma.model.relational.Turma;
import jakarta.persistence.*;

@Entity
@Table(name = "cursa", schema = "universidade")
public class Cursa {

    @EmbeddedId
    private CursaId id = new CursaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("matEstudante")
    @JoinColumn(name = "mat_estudante")
    private Estudante estudante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTurma")
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @Column(name = "nota")
    private Float nota;

    public Cursa() {}

    // getters e setters
    public CursaId getId() { return id; }
    public void setId(CursaId id) { this.id = id; }
    public Estudante getEstudante() { return estudante; }
    public void setEstudante(Estudante estudante) { this.estudante = estudante; }
    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
    public Float getNota() { return nota; }
    public void setNota(Float nota) { this.nota = nota; }
}