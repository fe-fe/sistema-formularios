package com.sistemaformulario.controller;

import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.service.UsuarioService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    // DTO interno para receber os dados
    private static class LoginDTO {
        public String email;
        public String senha;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LoginDTO loginData = JsonUtil.readJson(req, LoginDTO.class);
            Usuario usuario = usuarioService.autenticar(loginData.email, loginData.senha);

            if (usuario != null) {
                HttpSession session = req.getSession();
                session.setAttribute("usuarioLogado", usuario);

                usuario.setSenha(null);

                JsonUtil.sendJson(resp, usuario);
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonUtil.sendJson(resp, Map.of("erro", "Credenciais inválidas"));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", "JSON inválido"));
        }
    }
}