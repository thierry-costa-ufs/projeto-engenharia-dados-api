package com.ufs.engdados.infrastructure.mirror;

import com.ufs.engdados.domain.usuario.event.UsuarioSalvoEvent;
import com.ufs.engdados.domain.usuario.mapper.UsuarioMapper;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.repository.nosql.UsuarioNoSqlRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMongoMirrorListener {

    private final UsuarioNoSqlRepository noSqlRepository;

    public UsuarioMongoMirrorListener(UsuarioNoSqlRepository noSqlRepository) {
        this.noSqlRepository = noSqlRepository;
    }

    @EventListener
    public void handleUsuarioSalvoEvent(UsuarioSalvoEvent event) {
        try {
            UsuarioDocument usuarioMg = UsuarioMapper.toMongoDocument(event.dtoOriginal());

            // Garante o CPF correto vindo do banco relacional
            usuarioMg.setCpf(event.usuarioPostgres().getCpf());

            if (event.ehAtualizacao()) {
                // Se for atualização, busca o ID do documento existente para sobrescrevê-lo corretamente
                noSqlRepository.findByCpf(event.usuarioPostgres().getCpf())
                        .ifPresent(doc -> usuarioMg.setId(doc.getId()));
            }

            noSqlRepository.save(usuarioMg);
            System.out.println("[SUCESSO] MongoDB sincronizado via Evento para o CPF: " + event.usuarioPostgres().getCpf());
        } catch (Exception e) {
            // Em produção, isso poderia ir para uma fila de dead-letter (DLQ) ou tabela de retry
            System.err.println("[ALERTA CRÍTICO] Falha assíncrona ao espelhar no MongoDB para o CPF "
                    + event.usuarioPostgres().getCpf() + ": " + e.getMessage());
        }
    }
}