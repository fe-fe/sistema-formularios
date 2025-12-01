package com.sistemaformulario.service;

import com.sistemaformulario.dao.PerfilDAO;
import com.sistemaformulario.dto.PerfilDTO;
import com.sistemaformulario.entities.acesso.Perfil;

import java.util.List;
import java.util.stream.Collectors;

public class PerfilService {
    private PerfilDAO perfilDAO = new PerfilDAO();

    public List<PerfilDTO> listarTodos() {
        try {
            List<Perfil> perfis = perfilDAO.findAll();
            return perfis.stream()
                    .map(p -> new PerfilDTO(p.getId(), p.getNome()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar perfis", e);
        }
    }
}
