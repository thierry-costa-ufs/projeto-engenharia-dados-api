package com.ufs.engdados.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class SincronizadorDeSequence implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public SincronizadorDeSequence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // 1. Sincroniza o contador da Turma (já estava funcionando)
            String buscarSequenceSql = "SELECT schemaname || '.' || sequencename FROM pg_sequences WHERE sequencename LIKE '%turma%' LIMIT 1";
            List<String> sequences = jdbcTemplate.queryForList(buscarSequenceSql, String.class);

            if (!sequences.isEmpty()) {
                String nomeSequenceId = sequences.get(0);
                String sql = "SELECT setval('" + nomeSequenceId
                        + "', coalesce((SELECT MAX(id_turma) FROM universidade.turma), 1))";
                jdbcTemplate.execute(sql);
                System.out.println("✅ Contador da sequence '" + nomeSequenceId + "' sincronizado!");
            }

            // 2. O NOSSO ESPIÃO: Busca até 5 semestres reais que existem no banco de dados
            List<Map<String, Object>> semestresValidos = jdbcTemplate
                    .queryForList("SELECT ano, semestre FROM universidade.semestre LIMIT 5");
            System.out.println("🎓 SEMESTRES CADASTRADOS NO BANCO PARA VOCÊ USAR: " + semestresValidos);

        } catch (Exception e) {
            System.out.println("⚠️ Erro: " + e.getMessage());
        }
    }
}