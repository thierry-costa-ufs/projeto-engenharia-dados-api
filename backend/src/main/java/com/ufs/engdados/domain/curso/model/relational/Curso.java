package com.ufs.engdados.domain.curso.model.relational;

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
    private String grau;

    // Mudado para String para aceitar "Turno Indefinido" do banco
    @Column(nullable = false, columnDefinition = "universidade.tipo_turno")
    private String turno;

    @Column(length = 100)
    private String campus;

    // Mudado para String para aceitar "Graduação" do banco
    @Column(columnDefinition = "universidade.tipo_nivel")
    private String nivel;

    public Curso() {}

    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getGrau() { return grau; }
    public void setGrau(String grau) { this.grau = grau; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
}