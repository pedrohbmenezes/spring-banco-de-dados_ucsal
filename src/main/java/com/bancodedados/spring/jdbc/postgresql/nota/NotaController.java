package com.bancodedados.spring.jdbc.postgresql.nota;

import java.util.List;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaRepository repository;

    public NotaController(NotaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<NotaEntity> getAllNotas() {
        return repository.findAll();
    }

    @GetMapping("/com-alunos")
    public List<NotaDtoWithAluno> getAllNotasComAlunos() {
        return repository.findAllNotasWithAlunos();
    }

    @GetMapping("/{id}/com-aluno")
    public NotaDtoWithAluno getNotaComAlunoById(@PathVariable Long id) {
        return repository.findNotaWithAlunoById(id);
    }

    @GetMapping("/{id}")
    public NotaEntity getNotaById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public int createNota(@RequestBody NotaEntity nota) {
        return repository.save(nota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateNota(@PathVariable Long id, @RequestBody NotaEntity nota) {
        try {
            NotaEntity notaAtual = repository.findById(id);
            if (notaAtual != null) {
                // atualize cada campo caso assim tenha sido passado no body da requisição
                // ignorando campos nulos
                if (nota.getAlunoId() == null) {
                    nota.setAlunoId(notaAtual.getAlunoId());
                }
                if (nota.getDisciplina() == null) {
                    nota.setDisciplina(notaAtual.getDisciplina());
                }
                if (nota.getNota() == null) {
                    nota.setNota(notaAtual.getNota());
                }
                nota.setId(id);
                return ResponseEntity.ok(repository.update(nota));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public int deleteNota(@PathVariable Long id) {
        return repository.deleteById(id);
    }
}
