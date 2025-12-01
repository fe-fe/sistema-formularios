package com.sistemaformulario.controller;

import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.service.TurmaService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/aluno/matricula")
public class MatriculaServlet extends HttpServlet {

    private TurmaService turmaService = new TurmaService();

    public static class MatriculaRequest {
        public Long turmaId;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (aluno == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            MatriculaRequest dto = JsonUtil.readJson(req, MatriculaRequest.class);

            if (dto.turmaId == null) {
                throw new IllegalArgumentException("ID da turma é obrigatório.");
            }

            turmaService.matricular(dto.turmaId, aluno.getId());

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Matrícula realizada com sucesso!"));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }
}