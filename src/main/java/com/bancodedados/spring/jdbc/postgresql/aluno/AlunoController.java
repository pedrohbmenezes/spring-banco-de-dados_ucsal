package com.bancodedados.spring.jdbc.postgresql.aluno;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aluno")
public class AlunoController {
    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/entities")
    public List<AlunoEntity> getAllEntities() {
        return repository.findAll();
    }

    @GetMapping("/entities/{id}")
    public AlunoEntity getEntityById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping("/entities")
    public int createEntity(@RequestBody AlunoEntity entity) {
        AlunoEntity aluno = new AlunoEntity();
        aluno.setNome(entity.getNome());
        aluno.setEmail(entity.getEmail());
        aluno.setMatricula(entity.getMatricula());
        return repository.save(aluno);
    }

    @PutMapping("/entities/{id}")
    public int updateEntity(@PathVariable int id, @RequestBody AlunoEntity entity) {
        AlunoEntity aluno = new AlunoEntity();
        aluno.setId(id);
        aluno.setNome(entity.getNome());
        aluno.setEmail(entity.getEmail());
        aluno.setMatricula(entity.getMatricula());
        return repository.update(aluno);
    }

    @DeleteMapping("/entities/{id}")
    public int deleteEntity(@PathVariable Long id) {
        return repository.deleteById(id);
    }
}