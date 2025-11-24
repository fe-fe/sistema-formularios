package com.sistemaformulario.controller;

import com.sistemaformulario.entities.avaliacao.ProcessoAvaliativo;
import com.sistemaformulario.service.ProcessoAvaliativoService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/processos")
public class ProcessoAvaliativoServlet extends HttpServlet {

    private ProcessoAvaliativoService service = new ProcessoAvaliativoService();

    // DTO simples para criação
    private static class ProcessoDTO {
        public String descricao;
        public String dataInicio;
        public String dataFim;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProcessoAvaliativo> processos = service.listarTodos();
        JsonUtil.sendJson(resp, processos);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProcessoDTO dto = JsonUtil.readJson(req, ProcessoDTO.class);
            service.criar(dto.descricao, dto.dataInicio, dto.dataFim);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Processo criado"));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }
}