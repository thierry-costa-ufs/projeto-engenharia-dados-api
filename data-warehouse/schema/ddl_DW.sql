DROP SCHEMA IF EXISTS dw_universidade CASCADE;

CREATE SCHEMA dw_universidade;

CREATE TABLE dw_universidade.dim_departamento (
    id_departamento_sk SERIAL PRIMARY KEY,
    cod_depto VARCHAR(10) UNIQUE,
    nome VARCHAR(82)
);

CREATE TABLE dw_universidade.dim_disciplina (
    id_disciplina_sk SERIAL PRIMARY KEY,
    cod_disc VARCHAR(624) NOT NULL,
    nome VARCHAR(451) NOT NULL,
    depto_responsavel VARCHAR(307),
    creditos SMALLINT
);

CREATE TABLE dw_universidade.dim_professor (
    id_professor_sk SERIAL PRIMARY KEY,
    mat_professor VARCHAR(7),
    nome VARCHAR(56) NOT NULL,
    tipo_jornada_trabalho VARCHAR(19),
    formacao VARCHAR(14),
    departamento_lotacao VARCHAR(66)
);

CREATE TABLE dw_universidade.dim_semestre (
    id_semestre_sk SERIAL PRIMARY KEY,
    ano SMALLINT NOT NULL,
    periodo VARCHAR(10) NOT NULL
);

CREATE TABLE dw_universidade.fato_turmas (
    id_departamento_fk INT NOT NULL REFERENCES dw_universidade.dim_departamento(id_departamento_sk),
    id_disciplina_fk INT NOT NULL REFERENCES dw_universidade.dim_disciplina(id_disciplina_sk),
    id_professor_fk INT NOT NULL REFERENCES dw_universidade.dim_professor(id_professor_sk),
    id_semestre_fk INT NOT NULL REFERENCES dw_universidade.dim_semestre(id_semestre_sk),
    
    id_turma_original INT,
    numero_turma INT,
    
    total_discentes INT DEFAULT 0,
    media_notas REAL DEFAULT 0.0,
    aprovados INT DEFAULT 0,
    reprovados INT DEFAULT 0,
    
    PRIMARY KEY (id_departamento_fk, id_disciplina_fk, id_professor_fk, id_semestre_fk, numero_turma)
);

INSERT INTO dw_universidade.dim_departamento (id_departamento_sk, cod_depto, nome)
VALUES (0, 'N/A', 'Não Informado / N/A');

INSERT INTO dw_universidade.dim_disciplina (id_disciplina_sk, cod_disc, nome, depto_responsavel, creditos)
VALUES (0, 'N/A', 'Não Informado / N/A', 'N/A', 0);

INSERT INTO dw_universidade.dim_professor (id_professor_sk, mat_professor, nome, tipo_jornada_trabalho, formacao, departamento_lotacao)
VALUES (0, 'N/A', 'Não Informado / N/A', 'N/A', 'N/A', 'N/A');

INSERT INTO dw_universidade.dim_semestre (id_semestre_sk, ano, periodo)
VALUES (0, 0, 'N/A');