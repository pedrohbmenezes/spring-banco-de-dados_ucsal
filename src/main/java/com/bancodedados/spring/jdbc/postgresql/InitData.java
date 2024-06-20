package com.bancodedados.spring.jdbc.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bancodedados.spring.jdbc.postgresql.aluno.AlunoEntity;
import com.bancodedados.spring.jdbc.postgresql.aluno.AlunoRepository;
import com.bancodedados.spring.jdbc.postgresql.nota.NotaRepository;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Limpar dados das tabelas
        jdbcTemplate.update("DELETE FROM notas");

        jdbcTemplate.update("DELETE FROM alunos");

        // Dados fictícios de alunos
        AlunoEntity aluno1 = new AlunoEntity();
        aluno1.setNome("João Silva");
        aluno1.setEmail("joao@gmail.com");
        aluno1.setMatricula("123456");

        AlunoEntity aluno2 = new AlunoEntity();
        aluno2.setNome("Maria Souza");
        aluno2.setEmail("maria@gmail.com");
        aluno2.setMatricula("654321");

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

    }
}
