    package com.ufs.engdados.domain.curso.model.nosql;

    import com.ufs.engdados.domain.curso.enums.TipoGrau;
    import com.ufs.engdados.domain.curso.enums.TipoNivel;
    import com.ufs.engdados.domain.curso.enums.TipoTurno;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.Document;
    import org.springframework.data.mongodb.core.index.CompoundIndex;
    import org.springframework.data.mongodb.core.mapping.Field;

    @Document(collection = "cursos")
    @CompoundIndex(name = "uq_curso_nosql", def = "{'nome': 1, 'turno': 1, 'campus': 1, 'nivel': 1}", unique = true)
    public class CursoDocument {

        @Id
        private String id;

        @Field
        private Integer idCurso; // Mantido como Integer para espelhar a PK do Relacional

        @Field
        private String nome;

        @Field
        private TipoGrau grau;

        @Field
        private TipoTurno turno;

        @Field
        private String campus;

        @Field
        private TipoNivel nivel;

        // Construtores, Getters e Setters
        public CursoDocument() {}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        public Integer getIdCurso() { return idCurso; }
        public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public TipoGrau getGrau() { return grau; }
        public void setGrau(TipoGrau grau) { this.grau = grau; }
        public TipoTurno getTurno() { return turno; }
        public void setTurno(TipoTurno turno) { this.turno = turno; }
        public String getCampus() { return campus; }
        public void setCampus(String campus) { this.campus = campus; }
        public TipoNivel getNivel() { return nivel; }
        public void setNivel(TipoNivel nivel) { this.nivel = nivel; }
    }