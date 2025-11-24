package com.sistemaformulario.controller;

import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.service.SubmissaoService; // Usando este service já que não temos RelatorioService
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/relatorios/visualizar")
public class RelatorioServlet extends HttpServlet {

    // Como não há RelatorioService na lista, usaremos o SubmissaoService para buscar dados
    private SubmissaoService submissaoService = new SubmissaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = (Usuario) req.getSession().getAttribute("usuarioLogado");
        String turmaIdStr = req.getParameter("turmaId");

        if (usuarioLogado == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (turmaIdStr != null) {
            Long turmaId = Long.parseLong(turmaIdStr);

            // RF19 e RF20: Controle de visibilidade
            if (usuarioLogado.getPerfil().getNome().equals("ADMINISTRADOR")) {

                req.getRequestDispatcher("/WEB-INF/views/relatorio/visao_admin.html").forward(req, resp);

            } else if (usuarioLogado.getPerfil().getNome().equals("PROFESSOR")) {
                // Professor vê estatísticas consolidadas
                // Idealmente, aqui chamaria relatorioService.gerarEstatisticas(turmaId)

                req.getRequestDispatcher("/WEB-INF/views/relatorio/visao_professor.html").forward(req, resp);
            }
        } else {
            // Se não passou turmaId, exibe página para selecionar turma
            req.getRequestDispatcher("/WEB-INF/views/relatorio/selecao_turma.html").forward(req, resp);
        }
    }
}