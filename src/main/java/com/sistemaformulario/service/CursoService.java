package com.sistemaformulario.service;

import com.sistemaformulario.dao.CursoDAO;
import com.sistemaformulario.dto.CursoDTO;
import com.sistemaformulario.entities.academico.Curso;
import java.util.List;
import java.util.stream.Collectors;

public class CursoService {
    private CursoDAO cursoDAO = new CursoDAO();

    public void criar(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        cursoDAO.create(curso);
    }

    public List<CursoDTO> listar() {
        List<Curso> cursos = cursoDAO.findAll();
        return cursos.stream()
                .map(curso -> {
                    CursoDTO dto = new CursoDTO();
                    dto.setId(curso.getId());
                    dto.setNome(curso.getNome());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}