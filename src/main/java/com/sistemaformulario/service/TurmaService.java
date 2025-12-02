package com.sistemaformulario.service;

import com.sistemaformulario.dao.DisciplinaDAO;
import com.sistemaformulario.dao.TurmaDAO;
import com.sistemaformulario.dao.UsuarioDAO;
import com.sistemaformulario.dto.TurmaDTO;
import com.sistemaformulario.entities.academico.Disciplina;
import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.acesso.Usuario;

import java.util.List;

public class TurmaService {

    private TurmaDAO turmaDAO = new TurmaDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public List<Turma> buscarTurmasPorAluno(Long alunoId) {
        return turmaDAO.findByAlunoId(alunoId);
    }

    public Turma buscarPorId(Long id) {
        return turmaDAO.findById(id);
    }
    public void criarTurma(TurmaDTO dto) {
        Disciplina disciplina = disciplinaDAO.findById(dto.getDisciplinaId());
        Usuario professor = usuarioDAO.findById(dto.getProfessorId());

        if (disciplina == null || professor == null) throw new RuntimeException("Dados inválidos");

        Turma turma = new Turma();
        turma.setNome(dto.getNome());
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);

        turmaDAO.create(turma);
    }

    public List<Turma> listarTodas() {
        return turmaDAO.findAll();
    }

    public void matricular(Long turmaId, Long alunoId) {
        turmaDAO.matricularAluno(turmaId, alunoId);
    }
}