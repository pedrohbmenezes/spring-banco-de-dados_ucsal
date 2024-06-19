CREATE TABLE IF NOT EXISTS alunos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    matricula VARCHAR(50) NOT NULL
);


CREATE TABLE IF NOT EXISTS notas (
    id SERIAL PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    disciplina VARCHAR(255) NOT NULL,
    nota DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (aluno_id) REFERENCES alunos(id)
);

CREATE OR REPLACE VIEW aluno_notas_view AS
SELECT 
    n.id AS nota_id,
    n.aluno_id,
    n.disciplina,
    n.nota,
    a.nome AS aluno_nome,
    a.email AS aluno_email,
    a.matricula AS aluno_matricula
FROM 
    notas n
JOIN 
    alunos a ON n.aluno_id = a.id;


