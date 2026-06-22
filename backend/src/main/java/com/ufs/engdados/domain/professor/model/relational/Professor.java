package com.ufs.engdados.domain.professor.model.relational;

import com.ufs.engdados.domain.usuario.model.relational.Usuario;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "professor", schema = "universidade")
public class Professor {

    @Id
    @Column(name = "cpf") // CORREÇÃO: O nome da coluna no banco é 'cpf'
    private Long cpf;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "cpf")
    private Usuario usuario;

    @Column(name = "mat_professor", nullable = false, unique = true, length = 7)
    private String matricula;

    @Column(nullable = false, length = 5)
    private String departamento;

    @Column(nullable = false, columnDefinition = "universidade.tipo_formacao")
    @JdbcTypeCode(SqlTypes.OTHER)
    private String formacao;

    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "tipo_jornada_trabalho", nullable = false, columnDefinition = "universidade.tipo_jornada")
    @JdbcTypeCode(SqlTypes.OTHER)
    private String jornada;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    public Professor() {}

    // --- GETTERS E SETTERS ---
    public Long getCpf() { return cpf; }
    public void setCpf(Long cpf) { this.cpf = cpf; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
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