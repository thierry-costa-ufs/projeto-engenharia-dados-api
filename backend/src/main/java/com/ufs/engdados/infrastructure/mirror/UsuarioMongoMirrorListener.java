package com.ufs.engdados.infrastructure.mirror;

import com.ufs.engdados.domain.usuario.event.UsuarioDeletadoEvent;
import com.ufs.engdados.domain.usuario.event.UsuarioSalvoEvent;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.repository.nosql.UsuarioNoSqlRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UsuarioMongoMirrorListener {

    private final UsuarioNoSqlRepository noSqlRepository;
    private final MongoTemplate mongoTemplate;

    public UsuarioMongoMirrorListener(UsuarioNoSqlRepository noSqlRepository, MongoTemplate mongoTemplate) {
        this.noSqlRepository = noSqlRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleUsuarioSalvo(UsuarioSalvoEvent event) {
        try {
            Long cpf = event.dtoOriginal().cpf();
            Query query = new Query(Criteria.where("cpf").is(cpf));
            Update update = new Update()
                    .set("nome", event.dtoOriginal().nome())
                    .set("dataNascimento", event.dtoOriginal().dataNascimento())
                    .set("email", event.dtoOriginal().email())
                    .set("telefone", event.dtoOriginal().telefone())
                    .set("login", event.dtoOriginal().login())
                    .set("senha", event.dtoOriginal().senha());

            mongoTemplate.upsert(query, update, UsuarioDocument.class);
            System.out.println("[MongoDB] Usuário sincronizado de forma atômica. CPF: " + cpf);

        } catch (Exception e) {
            System.err.println("[MongoDB] ERRO CRÍTICO no espelhamento assíncrono do CPF: " + event.usuarioRelacional().getCpf());
            e.printStackTrace();
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleUsuarioDeletado(UsuarioDeletadoEvent event) {
        try {
            noSqlRepository.deleteByCpf(event.cpf());
            System.out.println("[MongoDB] Usuário removido do NoSQL. CPF: " + event.cpf());
        } catch (Exception e) {
            System.err.println("[MongoDB] Erro ao remover usuário do NoSQL: " + event.cpf());
            e.printStackTrace();
        }
    }
}