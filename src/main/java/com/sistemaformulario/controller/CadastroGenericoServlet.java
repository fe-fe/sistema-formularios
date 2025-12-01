package com.sistemaformulario.controller;

import com.sistemaformulario.dto.AutocadastroDTO;
import com.sistemaformulario.service.UsuarioService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/autocadastro")
public class CadastroGenericoServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            AutocadastroDTO dto = JsonUtil.readJson(req, AutocadastroDTO.class);

            usuarioService.autocadastrar(dto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Cadastro realizado com sucesso!"));

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
