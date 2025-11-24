package com.sistemaformulario.controller.admin;

import com.sistemaformulario.dto.TurmaDTO;
import com.sistemaformulario.service.TurmaService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/admin/turmas")
public class AdminTurmaServlet extends HttpServlet {

    private TurmaService turmaService = new TurmaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TurmaDTO dto = JsonUtil.readJson(req, TurmaDTO.class);
            turmaService.criarTurma(dto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Turma criada com sucesso"));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }
}