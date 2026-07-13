CREATE SCHEMA dw_universidade;

CREATE TABLE dw_universidade.dim_departamento (
    id_departamento_sk SERIAL PRIMARY KEY,
    cod_depto VARCHAR(10) UNIQUE,
    nome VARCHAR(100)
);

CREATE TABLE dw_universidade.dim_disciplina (
    id_disciplina_sk SERIAL PRIMARY KEY,
    cod_disc VARCHAR(624) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    depto_responsavel VARCHAR(10),
    creditos SMALLINT
);

CREATE TABLE dw_universidade.dim_professor (
    id_professor_sk SERIAL PRIMARY KEY,
    mat_professor VARCHAR(20),
    nome VARCHAR(150) NOT NULL,
    tipo_jornada_trabalho VARCHAR(50),
    formacao VARCHAR(100),
    departamento_lotacao VARCHAR(150)
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