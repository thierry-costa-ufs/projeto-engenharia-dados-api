    package com.ufs.engdados.domain.professor.model.nosql;

    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.Document;
    import org.springframework.data.mongodb.core.mapping.Field;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.List;

    @Document(collection = "professores")
    public class ProfessorDocument {

        @Id
        private String id;

        @Field(name = "mat_professor")
        private String matricula;
        @Field("cpf")
        private Long cpf;
        @Field("nome")
        private String nome;
        @Field("departamento")
        private String departamento;
        @Field("formacao")
        private String formacao;
        @Field("data_admissao")
        private LocalDate dataAdmissao;
        @Field("tipo_jornada_trabalho")
        private String jornada;
        @Field("salario")
        private BigDecimal salario;

        // Construtores
        public ProfessorDocument() {}

        // Getters e Setters

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getMatricula() { return matricula; }
        public void setMatricula(String matricula) { this.matricula = matricula; }

        public Long getCpf() { return cpf; }
        public void setCpf(Long cpf) { this.cpf = cpf; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getDepartamento() { return departamento; }
        public void setDepartamento(String departamento) { this.departamento = departamento; }

        public String getFormacao() { return formacao; }
        public void setFormacao(String formacao) { this.formacao = formacao; }

        public LocalDate getDataAdmissao() { return dataAdmissao; }
        public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }

        public String getJornada() { return jornada; }
        public void setJornada(String jornada) { this.jornada = jornada; }

        public BigDecimal getSalario() { return salario; }
        public void setSalario(BigDecimal salario) { this.salario = salario; }
    }