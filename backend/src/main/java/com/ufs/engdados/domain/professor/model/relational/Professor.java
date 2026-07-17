package com.ufs.engdados.domain.professor.model.relational;

import com.ufs.engdados.domain.professor.enums.TipoFormacao;
import com.ufs.engdados.domain.professor.enums.TipoJornada;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "professor", schema = "universidade")
public class Professor {

    @Id @Column(name = "mat_professor", nullable = false, unique = true, length = 7)
    private String matricula;

    @Column(name = "cpf")
    private Long cpf;

    @Column(nullable = false, length = 5)
    private String departamento;

    @Column(nullable = false, columnDefinition = "universidade.tipo_formacao")
    @ColumnTransformer(write = "?::universidade.tipo_formacao")
    private TipoFormacao formacao;

    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "tipo_jornada_trabalho", nullable = false, columnDefinition = "universidade.tipo_jornada")
    @ColumnTransformer(write = "?::universidade.tipo_jornada")
    private TipoJornada jornada;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    public Professor() {}

    // --- GETTERS E SETTERS ---
    public Long getCpf() { return cpf; }
    public void setCpf(Long cpf) { this.cpf = cpf; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public TipoFormacao getFormacao() { return formacao; }
    public void setFormacao(TipoFormacao formacao) { this.formacao = formacao; }

    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }

    public TipoJornada getJornada() { return jornada; }
    public void setJornada(TipoJornada jornada) { this.jornada = jornada; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }
}