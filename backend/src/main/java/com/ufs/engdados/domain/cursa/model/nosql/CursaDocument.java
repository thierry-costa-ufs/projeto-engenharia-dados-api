package com.ufs.engdados.domain.cursa.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cursa")
public class CursaDocument {

    @Id
    private String id;

    @Field(name = "mat_estudante")
    private String matEstudante;
    @Field(name = "id_turma")
    private Integer idTurma;
    @Field(name = "nota")
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