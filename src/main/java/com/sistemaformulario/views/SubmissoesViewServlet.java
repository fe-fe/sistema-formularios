package com.sistemaformulario.views;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/aluno/historico", "/professor/respostas"})
public class SubmissoesViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String caminho = req.getServletPath();

        if (caminho.equals("/aluno/historico")) {

            req.getRequestDispatcher("/WEB-INF/views/aluno/historico_aluno.html").forward(req, resp);
        }
        else if (caminho.equals("/professor/respostas")) {

            req.getRequestDispatcher("/WEB-INF/views/admin/submissoes_professor.html").forward(req, resp);
        }
    }
}