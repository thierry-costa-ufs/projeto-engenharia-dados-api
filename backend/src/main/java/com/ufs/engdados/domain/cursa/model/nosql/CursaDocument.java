package com.ufs.engdados.domain.cursa.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursa")
public class CursaDocument {

    @Id
    private String id; // gera uma string única unindo a matrícula e a turma

    private String matEstudante;
    private Integer idTurma;
    private Float nota;

    public CursaDocument() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMatEstudante() { return matEstudante; }
    public void setMatEstudante(String matEstudante) { this.matEstudante = matEstudante; }
    public Integer getIdTurma() { return idTurma; }
    public void setIdTurma(Integer idTurma) { this.idTurma = idTurma; }
    public Float getNota() { return nota; }
    public void setNota(Float nota) { this.nota = nota; }
}