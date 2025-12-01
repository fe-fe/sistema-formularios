package com.sistemaformulario.controller;

import com.sistemaformulario.dto.FormularioCreationDTO;
import com.sistemaformulario.entities.avaliacao.Formulario;
import com.sistemaformulario.service.FormularioService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/formularios")
public class CriarFormularioServlet extends HttpServlet {

    private FormularioService formService = new FormularioService();

    // GET: Lista todos ou busca um específico por ID
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        try {
            if (idStr != null && !idStr.isEmpty()) {
                // Read One (retorna o formulário completo com questões/alternativas)
                Formulario form = formService.buscarPorId(idStr);
                if (form != null) {
                    JsonUtil.sendJson(resp, form);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.sendJson(resp, Map.of("erro", "Formulário não encontrado."));
                }
            } else {
                // Read All
                List<Formulario> formularios = formService.listarTodos();
                JsonUtil.sendJson(resp, formularios);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendJson(resp, Map.of("erro", "Erro ao buscar dados: " + e.getMessage()));
        }
    }

    // POST: Cria um novo formulário
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            FormularioCreationDTO dto = JsonUtil.readJson(req, FormularioCreationDTO.class);

            formService.criarFormulario(dto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Formulário criado com sucesso"));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }

    // PUT: Atualiza um formulário existente
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", "ID do formulário é obrigatório para atualização."));
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            FormularioCreationDTO dto = JsonUtil.readJson(req, FormularioCreationDTO.class);

            formService.atualizarFormulario(id, dto);

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Formulário atualizado com sucesso"));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }

    // DELETE: Remove um formulário
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", "ID do formulário é obrigatório para exclusão."));
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            formService.excluirFormulario(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }
}