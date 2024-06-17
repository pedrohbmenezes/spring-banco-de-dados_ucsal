package com.bancodedados.spring.jdbc.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bancodedados.spring.jdbc.postgresql.aluno.AlunoRepository;
import com.bancodedados.spring.jdbc.postgresql.nota.NotaEntity;
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


        // Dados fictícios de notas
        NotaEntity nota1 = new NotaEntity();
        nota1.setAlunoId(9L); // Associar com aluno João Silva
        nota1.setDisciplina("Matemática");
        nota1.setNota(9.5);

        NotaEntity nota2 = new NotaEntity();
        nota2.setAlunoId(10L); // Associar com aluno Maria Souza
        nota2.setDisciplina("Português");
        nota2.setNota(8.0);

        // Inserir notas
        notaRepository.save(nota1);
        notaRepository.save(nota2);

        System.out.println("Dados fictícios de notas e alunos inseridos com sucesso!");
    }
}
