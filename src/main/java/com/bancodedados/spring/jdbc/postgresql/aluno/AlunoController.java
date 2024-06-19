package com.bancodedados.spring.jdbc.postgresql.aluno;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {
    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<AlunoEntity> getAllEntities() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public AlunoEntity getEntityById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping("")
    public int createEntity(@RequestBody AlunoEntity entity) {
        AlunoEntity aluno = new AlunoEntity();
        aluno.setNome(entity.getNome());
        aluno.setEmail(entity.getEmail());
        aluno.setMatricula(entity.getMatricula());
        return repository.save(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateEntity(@PathVariable int id, @RequestBody AlunoEntity entity) {
        AlunoEntity aluno = repository.findById(Long.valueOf(id));
        if (aluno != null) {
            entity.setId(id);

            if (entity.getNome() != null) {
                aluno.setNome(entity.getNome());
            }
            if (entity.getEmail() != null) {
                aluno.setEmail(entity.getEmail());
            }

            if (entity.getMatricula() != null) {
                aluno.setMatricula(entity.getMatricula());
            }
            return ResponseEntity.ok(repository.update(aluno));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public int deleteEntity(@PathVariable Long id) {
        return repository.deleteById(id);
    }
}