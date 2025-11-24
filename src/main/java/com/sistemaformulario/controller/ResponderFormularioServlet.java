package com.sistemaformulario.controller;

import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.entities.avaliacao.Formulario;
import com.sistemaformulario.service.FormularioService;
import com.sistemaformulario.service.ParticipacaoService;
import com.sistemaformulario.service.SubmissaoService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/aluno/responder")
public class ResponderFormularioServlet extends HttpServlet {

    private FormularioService formularioService = new FormularioService();
    private SubmissaoService submissaoService = new SubmissaoService();
    private ParticipacaoService participacaoService = new ParticipacaoService();

    // DTO para receber a resposta do JSON
    private static class RespostaEnvioDTO {
        public Long turmaId;
        public Long formularioId;
        // Mapa onde Chave = "questao_ID" e Valor = Array de Strings (respostas)
        public Map<String, String[]> respostas;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");
        String turmaIdStr = req.getParameter("turmaId");
        String formularioIdStr = req.getParameter("formularioId");

        if (aluno == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long turmaId = Long.parseLong(turmaIdStr);
        Long formularioId = Long.parseLong(formularioIdStr);

        boolean jaRespondeu = participacaoService.verificarParticipacao(aluno.getId(), turmaId, formularioId);

        if (jaRespondeu) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            JsonUtil.sendJson(resp, Map.of("erro", "Você já respondeu este formulário."));
            return;
        }

        Formulario form = formularioService.buscarPorId(formularioIdStr);
        // Retorna o objeto formulário completo
        JsonUtil.sendJson(resp, form);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (aluno == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            // Lendo o JSON enviado pelo frontend
            RespostaEnvioDTO dto = JsonUtil.readJson(req, RespostaEnvioDTO.class);

            // CONVERSÃO NECESSÁRIA:
            // O Service espera um Map<String, String[]> parecido com o request.getParameterMap()
            // Vamos recriar esse mapa manualmente a partir do DTO para reaproveitar o Service existente.

            Map<String, String[]> parametrosConvertidos = new HashMap<>();
            parametrosConvertidos.put("turmaId", new String[]{String.valueOf(dto.turmaId)});
            parametrosConvertidos.put("formularioId", new String[]{String.valueOf(dto.formularioId)});

            if (dto.respostas != null) {
                parametrosConvertidos.putAll(dto.respostas);
            }

            submissaoService.processarEnvio(aluno, parametrosConvertidos);

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Sucesso"));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }
}