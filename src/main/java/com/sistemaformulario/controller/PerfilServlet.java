package com.sistemaformulario.controller;

import com.sistemaformulario.dto.PerfilDTO;
import com.sistemaformulario.service.PerfilService;
import com.sistemaformulario.util.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/perfis")
public class PerfilServlet extends HttpServlet {
    private PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Configurar CORS para desenvolvimento
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PerfilDTO> perfis = perfilService.listarTodos();
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.sendJson(resp, perfis);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendJson(resp, Map.of(
                    "erro", "Erro ao buscar perfis",
                    "detalhes", e.getMessage()
            ));
        }
    }
}
