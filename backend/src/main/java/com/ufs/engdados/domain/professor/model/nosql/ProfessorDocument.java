    package com.ufs.engdados.domain.professor.model.nosql;

    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.Document;
    import org.springframework.data.mongodb.core.mapping.Field;
    import java.time.LocalDate;
    import java.util.List;

    @Document(collection = "professor")
    public class ProfessorDocument {

        @Id
        private String matricula;

        @Field("cpf")
        private Long cpf;

        @Field("nome")
        private String nome;

        @Field("email")
        private List<String> emails;

        @Field("departamento")
        private String departamento;

        @Field("formacao")
        private String formacao;

        @Field("data_admissao")
        private LocalDate dataAdmissao;

        @Field("tipo_jornada_trabalho")
        private String tipoJornadaTrabalho;

        @Field("salario")
        private Double salario;

        // Construtores
        public ProfessorDocument() {}

        // Getters e Setters
        public String getMatricula() { return matricula; }
        public void setMatricula(String matricula) { this.matricula = matricula; }

        public Long getCpf() { return cpf; }
        public void setCpf(Long cpf) { this.cpf = cpf; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public List<String> getEmails() { return emails; }
        public void setEmails(List<String> emails) { this.emails = emails; }

        public String getDepartamento() { return departamento; }
        public void setDepartamento(String departamento) { this.departamento = departamento; }

        public String getFormacao() { return formacao; }
        public void setFormacao(String formacao) { this.formacao = formacao; }

        public LocalDate getDataAdmissao() { return dataAdmissao; }
        public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }

        public String getTipoJornadaTrabalho() { return tipoJornadaTrabalho; }
        public void setTipoJornadaTrabalho(String tipoJornadaTrabalho) { this.tipoJornadaTrabalho = tipoJornadaTrabalho; }

        public Double getSalario() { return salario; }
        public void setSalario(Double salario) { this.salario = salario; }
    }