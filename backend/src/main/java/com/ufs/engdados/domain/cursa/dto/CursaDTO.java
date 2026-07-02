package com.ufs.engdados.domain.cursa.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CursaDTO(

        @JsonProperty("matEstudante")
        @JsonAlias({"mat_estudante", "matriculaEstudante", "matricula"})
        String matEstudante,

        @JsonProperty("idTurma")
        @JsonAlias({"id_turma", "turmaId"})
        Integer idTurma,

        @JsonProperty("nota")
        @JsonAlias({"media", "notaFinal", "nota1"})
        Float nota
) {
        // Cria um ID virtual juntando a matrícula e a turma (ex: "E102/7")
        @JsonProperty("id")
        public String getId() {
                if (matEstudante != null && idTurma != null) {
                        return matEstudante + "/" + idTurma;
                }
                return null;
        }
}