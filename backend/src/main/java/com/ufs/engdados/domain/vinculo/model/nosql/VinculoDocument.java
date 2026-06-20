package com.ufs.engdados.domain.vinculo.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vinculo")
@CompoundIndexes({
        @CompoundIndex(name = "idx_vinculo_estudante_curso", def = "{'matEstudante': 1, 'codCurso': 1}", unique = false)
})
public class VinculoDocument {

    @Id
    private String id;

    private Long idRelacional; // Mapeia o ID gerado no Postgres
    private String matEstudante;
    private Long codCurso;
    private String semestre;
    private String situacao;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getIdRelacional() { return idRelacional; }
    public void setIdRelacional(Long idRelacional) { this.idRelacional = idRelacional; }
    public String getMatEstudante() { return matEstudante; }
    public void setMatEstudante(String matEstudante) { this.matEstudante = matEstudante; }
    public Long getCodCurso() { return codCurso; }
    public void setCodCurso(Long codCurso) { this.codCurso = codCurso; }
    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}