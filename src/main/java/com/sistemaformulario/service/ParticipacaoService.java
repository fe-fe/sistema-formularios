package com.sistemaformulario.service;

import com.sistemaformulario.dao.ParticipacaoDAO;

public class ParticipacaoService {

    private ParticipacaoDAO controleDAO = new ParticipacaoDAO();

    public boolean verificarParticipacao(Long alunoId, Long turmaId, Long formularioId) {
        return controleDAO.hasStudentReplied(alunoId, turmaId, formularioId);
    }
}