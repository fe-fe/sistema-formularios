package com.sistemaformulario.controller;

import com.sistemaformulario.dto.CadastroDTO;
import com.sistemaformulario.service.UsuarioService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/cadastro")
public class CadastroServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CadastroDTO dto = JsonUtil.readJson(req, CadastroDTO.class);

            usuarioService.cadastrarAluno(dto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Cadastro realizado com sucesso!"));

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}