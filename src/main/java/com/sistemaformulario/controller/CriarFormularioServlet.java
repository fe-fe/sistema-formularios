package com.sistemaformulario.controller;

import com.sistemaformulario.dto.FormularioCreationDTO;
import com.sistemaformulario.service.FormularioService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/admin/formularios")
public class CriarFormularioServlet extends HttpServlet {

    private FormularioService formService = new FormularioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Jackson converte o JSON direto para seu DTO
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
}