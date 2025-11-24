package com.sistemaformulario.service;

import com.sistemaformulario.dao.CursoDAO;
import com.sistemaformulario.dao.DisciplinaDAO;
import com.sistemaformulario.dto.CursoDTO;
import com.sistemaformulario.dto.DisciplinaDTO;
import com.sistemaformulario.entities.academico.Curso;
import com.sistemaformulario.entities.academico.Disciplina;
import java.util.List;

public class CursoService {
    private CursoDAO cursoDAO = new CursoDAO();

    public void criar(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        cursoDAO.create(curso);
    }

    public List<Curso> listar() { return cursoDAO.findAll(); }
}