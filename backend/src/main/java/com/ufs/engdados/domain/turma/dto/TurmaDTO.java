package com.ufs.engdados.domain.turma.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TurmaDTO(

        @JsonProperty("idTurma")
        @JsonAlias("id_turma")
        Integer idTurma,

        @JsonProperty("codigoDisciplina") // o que a tabela lê
        @JsonAlias({"cod_disc", "codDisc"}) // o que o formulário envia
        String codDisc,

        @JsonProperty("codigoTurma") // o que a tabela lê
        @JsonAlias("numero") // o que o formulário envia
        Integer numero,

        Integer ano,

        Integer semestre
) {
        // para o botão de editar e deletar do React funcionar
        @JsonProperty("id")
        public Integer getId() {
                return idTurma;
        }
}