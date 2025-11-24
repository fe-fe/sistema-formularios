package com.sistemaformulario.controller;

import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.service.TurmaService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/aluno/dashboard")
public class PainelAlunoServlet extends HttpServlet {

    private TurmaService turmaService = new TurmaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (aluno == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.sendJson(resp, Map.of("erro", "Não autenticado"));
            return;
        }

        List<Turma> turmas = turmaService.buscarTurmasPorAluno(aluno.getId());

        // Dica: Se o JSON falhar por recursão infinita, crie um DTO simples aqui e converta a lista
        JsonUtil.sendJson(resp, turmas);
    }
}