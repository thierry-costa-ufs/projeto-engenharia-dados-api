package com.ufs.engdados.domain.curso.model.relational;

import com.ufs.engdados.domain.curso.enums.TipoGrau;
import com.ufs.engdados.domain.curso.enums.TipoNivel;
import com.ufs.engdados.domain.curso.enums.TipoTurno;
import jakarta.persistence.*;

@Entity
@Table(name = "curso", schema = "universidade", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "turno", "campus", "nivel"})
})
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcurso")
    private Integer idCurso;

    @Column(nullable = false, length = 100)
    private String nome;

    // Mudado para String para aceitar "Licenciatura Plena" do banco
    @Column(columnDefinition = "universidade.tipo_grau")
    private TipoGrau grau;

    // Mudado para String para aceitar "Turno Indefinido" do banco
    @Column(nullable = false, columnDefinition = "universidade.tipo_turno")
    private TipoTurno turno;

    @Column(length = 100)
    private String campus;

    // Mudado para String para aceitar "Graduação" do banco
    @Column(columnDefinition = "universidade.tipo_nivel")
    private TipoNivel nivel;

    public Curso() {}

    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoGrau getGrau() { return grau; }
    public void setGrau(TipoGrau grau) { this.grau = grau; }
    public TipoTurno getTurno() { return turno; }
    public void setTurno(TipoTurno turno) { this.turno = turno; }
    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }
    public TipoNivel getNivel() { return nivel; }
    public void setNivel(TipoNivel nivel) { this.nivel = nivel; }
}