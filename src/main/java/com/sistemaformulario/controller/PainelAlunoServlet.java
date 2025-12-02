package com.sistemaformulario.controller;

import com.sistemaformulario.dto.DashboardItemDTO;
import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.entities.avaliacao.Formulario;
import com.sistemaformulario.service.FormularioService;
import com.sistemaformulario.service.TurmaService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/api/aluno/dashboard")
public class PainelAlunoServlet extends HttpServlet {

    private TurmaService turmaService = new TurmaService();
    private FormularioService formularioService = new FormularioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (aluno == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.sendJson(resp, Map.of("erro", "Não autenticado"));
            return;
        }

        List<Turma> turmas = turmaService.buscarTurmasPorAluno(aluno.getId());
        List<DashboardItemDTO> dashboardData = new ArrayList<>();

        for (Turma t : turmas) {
            List<Formulario> pendentes = formularioService.buscarPendentes(aluno.getId(), t.getId());
            dashboardData.add(new DashboardItemDTO(t, pendentes));
        }

        JsonUtil.sendJson(resp, dashboardData);
    }
}