package com.sistemaformulario.servlets; // Altere para o seu pacote

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet de teste simples que retorna um HTML básico.
 * A anotação @WebServlet mapeia este Servlet para a URL /hello
 */
@WebServlet("/hello")
public class TesteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Configurar o tipo de conteúdo da resposta
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // 2. Obter o objeto PrintWriter para enviar a resposta
        PrintWriter out = response.getWriter();

        // 3. Escrever o conteúdo HTML
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Teste Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Olá do Meu Primeiro Servlet!</h1>");
        out.println("<p>Se você vê esta página, seu Servlet está funcionando.</p>");
        out.println("</body>");
        out.println("</html>");
    }

    // Você pode omitir o método doPost por enquanto, a menos que precise lidar com formulários POST.
}