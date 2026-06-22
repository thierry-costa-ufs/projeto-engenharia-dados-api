package com.ufs.engdados.domain.curso.listener;

import com.ufs.engdados.domain.curso.enums.TipoGrau;
import com.ufs.engdados.domain.curso.enums.TipoNivel;
import com.ufs.engdados.domain.curso.enums.TipoTurno;
import com.ufs.engdados.domain.curso.event.CursoDeletadoEvent;
import com.ufs.engdados.domain.curso.event.CursoSalvoEvent;
import com.ufs.engdados.domain.curso.model.nosql.CursoDocument;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CursoMongoMirrorListener {

    private final MongoTemplate mongoTemplate;

    public CursoMongoMirrorListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Async("customTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCursoSalvo(CursoSalvoEvent event) {
        // Query de Busca pela Chave Primária de Negócio
        Query query = new Query(Criteria.where("_id").is(event.idCurso()));

        // Mapeamento e resolução de enums a partir das strings limpas do DTO primitivo
        Update update = new Update()
                .set("nome", event.dtoOriginal().nome())
                .set("grau", TipoGrau.deString(event.dtoOriginal().grau()))
                .set("turno", TipoTurno.deString(event.dtoOriginal().turno()))
                .set("campus", event.dtoOriginal().campus())
                .set("nivel", TipoNivel.deString(event.dtoOriginal().nivel()));

        // Execução Atômica de Upsert (Proteção contra Condições de Corrida)
        mongoTemplate.upsert(query, update, CursoDocument.class);
    }

    @Async("customTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCursoDeletado(CursoDeletadoEvent event) {
        Query query = new Query(Criteria.where("_id").is(event.idCurso()));
        mongoTemplate.remove(query, CursoDocument.class);
    }
}