package com.bancodedados.spring.jdbc.postgresql.nota;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bancodedados.spring.jdbc.postgresql.GenericRepository;

@Repository
public class NotaRepository implements GenericRepository<NotaEntity> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Logger log = LoggerFactory.getLogger(NotaRepository.class);

    @Override
    public int save(NotaEntity nota) {
        return jdbcTemplate.update("INSERT INTO notas (aluno_id, disciplina, nota) VALUES(?,?,?)",
                new Object[] { nota.getAlunoId(), nota.getDisciplina(), nota.getNota() });
    }

    @Override
    public int update(NotaEntity nota) {
        return jdbcTemplate.update("UPDATE notas SET aluno_id=?, disciplina=?, nota=? WHERE id=?",
                new Object[] { nota.getAlunoId(), nota.getDisciplina(), nota.getNota(), nota.getId() });
    }

    @Override
    public NotaEntity findById(Long id) {
        try {
            NotaEntity nota = jdbcTemplate.queryForObject("SELECT * FROM notas WHERE id=?",
                    BeanPropertyRowMapper.newInstance(NotaEntity.class), id);
            return nota;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM notas WHERE id=?", id);
    }

    @Override
    public List<NotaEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM notas", BeanPropertyRowMapper.newInstance(NotaEntity.class));
    }

    @Override
    public List<NotaEntity> findByPublished(boolean published) {
        // Implementação opcional, dependendo do seu caso de uso
        return null;
    }

    @Override
    public List<NotaEntity> findByTitleContaining(String title) {
        // Implementação opcional, dependendo do seu caso de uso
        return null;
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM notas");
    }

    public List<NotaDtoWithAluno> findAllNotasWithAlunos() {
        String sql = "SELECT n.id, n.aluno_id, n.disciplina, n.nota, " +
                "a.nome as aluno_nome, a.email as aluno_email, a.matricula as aluno_matricula " +
                "FROM notas n " +
                "JOIN alunos a ON n.aluno_id = a.id";
        List<NotaDtoWithAluno> notas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(NotaDtoWithAluno.class));
        log.info("Notas: {}", notas.toString());
        return notas;
    }

    // Método para obter uma nota específica com informações do aluno através de um
    // JOIN
    public NotaDtoWithAluno findNotaWithAlunoById(Long id) {
        String sql = "SELECT n.id, n.aluno_id, n.disciplina, n.nota, " +
                "a.nome as aluno_nome, a.email as aluno_email, a.matricula as aluno_matricula " +
                "FROM notas n " +
                "JOIN alunos a ON n.aluno_id = a.id " +
                "WHERE n.id = ?";
        try {
            NotaDtoWithAluno nota = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(NotaDtoWithAluno.class), id);
            return nota;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

    }
}
