package com.sistemaformulario.service;

import com.sistemaformulario.dao.FormularioDAO;
import com.sistemaformulario.dao.PerfilDAO;
import com.sistemaformulario.dao.ProcessoAvaliativoDAO;
import com.sistemaformulario.dto.AlternativaDTO;
import com.sistemaformulario.dto.FormularioCreationDTO;
import com.sistemaformulario.dto.QuestaoDTO;
import com.sistemaformulario.entities.acesso.Perfil;
import com.sistemaformulario.entities.avaliacao.Alternativa;
import com.sistemaformulario.entities.avaliacao.Formulario;
import com.sistemaformulario.entities.avaliacao.ProcessoAvaliativo;
import com.sistemaformulario.entities.avaliacao.Questao;

import java.util.ArrayList;
import java.util.List;

public class FormularioService {

    private FormularioDAO formularioDAO = new FormularioDAO();
    private ProcessoAvaliativoDAO processoDAO = new ProcessoAvaliativoDAO();
    private PerfilDAO perfilDAO = new PerfilDAO();

    public List<Formulario> listarTodos() {
        return formularioDAO.findAll();
    }

    public Formulario buscarPorId(String idStr) {
        if (idStr == null || idStr.isEmpty()) return null;
        return formularioDAO.findById(Long.parseLong(idStr));
    }

    public void excluirFormulario(Long id) {
        formularioDAO.deleteById(id);
    }

    public void atualizarFormulario(Long id, FormularioCreationDTO dto) {
        Formulario formulario = formularioDAO.findById(id);
        if (formulario == null) {
            throw new IllegalArgumentException("Formulário não encontrado.");
        }

        ProcessoAvaliativo processo = processoDAO.findById(dto.getProcessoId());
        if (processo == null) {
            throw new IllegalArgumentException("Processo Avaliativo inválido ou não encontrado.");
        }

        Perfil perfilDestino = null;
        if (dto.getPerfilDestinoId() != null) {
            perfilDestino = perfilDAO.findById(dto.getPerfilDestinoId());
        }

        formulario.setTitulo(dto.getTitulo());
        formulario.setAnonimo(dto.isAnonimo());
        formulario.setProcessoAvaliativo(processo);
        formulario.setPerfilDestino(perfilDestino);

        // Limpar a lista antiga para que o JPA/Hibernate faça o merge/delete em cascata
        formulario.getQuestoes().clear();
        formulario.setQuestoes(new ArrayList<>());

        if (dto.getQuestoes() != null) {
            for (QuestaoDTO qDto : dto.getQuestoes()) {
                Questao questao = new Questao();
                questao.setEnunciado(qDto.getEnunciado());
                questao.setTipo(qDto.getTipo());
                questao.setObrigatoria(qDto.isObrigatoria());
                questao.setId(qDto.getId()); // Passa o ID se edição de Questão existente

                questao.setFormulario(formulario);
                questao.setAlternativas(new ArrayList<>());

                if (qDto.getAlternativas() != null) {
                    for (AlternativaDTO aDto : qDto.getAlternativas()) {
                        Alternativa alternativa = new Alternativa();
                        alternativa.setTexto(aDto.getTexto());
                        alternativa.setPeso(aDto.getPeso());
                        alternativa.setId(aDto.getId()); // Passa o ID se edição de Alternativa existente

                        alternativa.setQuestao(questao);

                        questao.getAlternativas().add(alternativa);
                    }
                }

                formulario.getQuestoes().add(questao);
            }
        }

        formularioDAO.update(formulario);
    }

    public void criarFormulario(FormularioCreationDTO dto) {
        ProcessoAvaliativo processo = processoDAO.findById(dto.getProcessoId());
        if (processo == null) {
            throw new IllegalArgumentException("Processo Avaliativo inválido ou não encontrado.");
        }

        Perfil perfilDestino = null;
        if (dto.getPerfilDestinoId() != null) {
            perfilDestino = perfilDAO.findById(dto.getPerfilDestinoId());
        }

        Formulario formulario = new Formulario();
        formulario.setTitulo(dto.getTitulo());
        formulario.setAnonimo(dto.isAnonimo());
        formulario.setProcessoAvaliativo(processo);
        formulario.setPerfilDestino(perfilDestino);
        formulario.setQuestoes(new ArrayList<>());

        if (dto.getQuestoes() != null) {
            for (QuestaoDTO qDto : dto.getQuestoes()) {
                Questao questao = new Questao();
                questao.setEnunciado(qDto.getEnunciado());
                questao.setTipo(qDto.getTipo());
                questao.setObrigatoria(qDto.isObrigatoria());

                questao.setFormulario(formulario);
                questao.setAlternativas(new ArrayList<>());

                if (qDto.getAlternativas() != null) {
                    for (AlternativaDTO aDto : qDto.getAlternativas()) {
                        Alternativa alternativa = new Alternativa();
                        alternativa.setTexto(aDto.getTexto());
                        alternativa.setPeso(aDto.getPeso());

                        alternativa.setQuestao(questao);

                        questao.getAlternativas().add(alternativa);
                    }
                }

                formulario.getQuestoes().add(questao);
            }
        }

        formularioDAO.create(formulario);
    }
}