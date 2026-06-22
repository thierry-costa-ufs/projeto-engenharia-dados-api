package com.ufs.engdados.infrastructure.mirror;

import com.ufs.engdados.domain.professor.event.ProfessorDeletadoEvent;
import com.ufs.engdados.domain.professor.event.ProfessorSalvoEvent;
import com.ufs.engdados.domain.professor.model.nosql.PerfilProfessor;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.repository.nosql.UsuarioNoSqlRepository;
import com.ufs.engdados.domain.usuario.repository.relational.UsuarioRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProfessorMongoMirrorListener {

    private final UsuarioNoSqlRepository usuarioNoSqlRepository;
    private final UsuarioRelationalRepository usuarioRelationalRepository;

    public ProfessorMongoMirrorListener(UsuarioNoSqlRepository usuarioNoSqlRepository,
                                        UsuarioRelationalRepository usuarioRelationalRepository) {
        this.usuarioNoSqlRepository = usuarioNoSqlRepository;
        this.usuarioRelationalRepository = usuarioRelationalRepository;
    }

    @Async("customTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProfessorSalvo(ProfessorSalvoEvent event) {
        UsuarioDocument usuarioDoc = usuarioNoSqlRepository.findByCpf(event.cpf())
                .orElseGet(() -> criarCascaUsuario(event.cpf()));

        PerfilProfessor perfil = new PerfilProfessor();
        perfil.setMatricula(event.dtoOriginal().matricula());
        perfil.setDepartamento(event.dtoOriginal().departamento());
        perfil.setFormacao(event.dtoOriginal().formacao());
        perfil.setJornada(event.dtoOriginal().jornada());
        perfil.setSalario(event.dtoOriginal().salario());
        perfil.setDataAdmissao(event.dtoOriginal().dataAdmissao());

        usuarioDoc.setPerfilProfessor(perfil);
        usuarioNoSqlRepository.save(usuarioDoc);
    }

    @Async("customTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProfessorDeletado(ProfessorDeletadoEvent event) {
        usuarioNoSqlRepository.findByCpf(event.cpf()).ifPresent(usuarioDoc -> {
            usuarioDoc.setPerfilProfessor(null);
            usuarioNoSqlRepository.save(usuarioDoc);
        });
    }

    private UsuarioDocument criarCascaUsuario(Long cpf) {
        return usuarioRelationalRepository.findById(cpf)
                .map(u -> {
                    UsuarioDocument doc = new UsuarioDocument();
                    doc.setCpf(u.getCpf());
                    doc.setNome(u.getNome());
                    doc.setDataNascimento(u.getDataNascimento());
                    doc.setEmail(u.getEmail());
                    doc.setTelefone(u.getTelefone());
                    doc.setLogin(u.getLogin());
                    doc.setSenha(u.getSenha());
                    return doc;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuário base não encontrado no Relacional para espelhamento: " + cpf));
    }
}