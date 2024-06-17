package com.bancodedados.spring.jdbc.postgresql.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bancodedados.spring.jdbc.postgresql.GenericRepository;

@Repository
public class AlunoRepository implements GenericRepository<AlunoEntity> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(AlunoEntity aluno) {
        return jdbcTemplate.update("INSERT INTO alunos (nome, email, matricula) VALUES(?,?,?)",
                new Object[] { aluno.getNome(), aluno.getEmail(), aluno.getMatricula() });
    }

    @Override
    public int update(AlunoEntity aluno) {
        return jdbcTemplate.update("UPDATE alunos SET nome=?, email=?, matricula=? WHERE id=?",
                new Object[] { aluno.getNome(), aluno.getEmail(), aluno.getMatricula(), aluno.getId() });
    }

    @Override
    public AlunoEntity findById(Long id) {
        try {
            AlunoEntity aluno = jdbcTemplate.queryForObject("SELECT * FROM alunos WHERE id=?",
                    BeanPropertyRowMapper.newInstance(AlunoEntity.class), id);

            return aluno;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM alunos WHERE id=?", id);
    }

    @Override
    public List<AlunoEntity> findAll() {
        return jdbcTemplate.query("SELECT * from alunos", BeanPropertyRowMapper.newInstance(AlunoEntity.class));
    }

    @Override
    public List<AlunoEntity> findByPublished(boolean published) {
        return jdbcTemplate.query("SELECT * from alunos WHERE published=?",
                BeanPropertyRowMapper.newInstance(AlunoEntity.class), published);
    }

    @Override
    public List<AlunoEntity> findByTitleContaining(String title) {
        String q = "SELECT * from alunos WHERE nome ILIKE '%" + title + "%'";

        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(AlunoEntity.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from alunos");
    }
}
