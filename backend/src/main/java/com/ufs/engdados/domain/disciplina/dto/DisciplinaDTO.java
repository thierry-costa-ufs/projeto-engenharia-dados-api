package com.ufs.engdados.domain.disciplina.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Diz ao Java: "Se o React enviar campos a mais, apenas ignore e não dê erro"
public record DisciplinaDTO(

        @JsonProperty("codigoDisciplina")
        @JsonAlias({"cod_disc", "codigo"})
        String codDisc,

        String nome,

        Integer creditos,

        @JsonProperty("pre_req")
        @JsonAlias({"preReq"})
        String preReq,

        @JsonProperty("codDepartamento")
        @JsonAlias({"depto_responsavel", "departamento"})
        String deptoResponsavel
) {
        // cria um campo "id" virtual no JSON só para o React conseguir montar a URL
        @JsonProperty("id")
        public String getId() {
                return codDisc;
        }
}