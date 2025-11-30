package com.sistemaformulario.views;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/formulario-novo")
public class CriarFormularioViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String processoId = request.getParameter("processoId");

        if (processoId == null || processoId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "É necessário selecionar um Processo Avaliativo primeiro.");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/cadastro_formulario.html").forward(request, response);
    }
}