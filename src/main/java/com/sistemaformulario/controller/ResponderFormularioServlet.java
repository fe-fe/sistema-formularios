package com.sistemaformulario.controller;

import com.sistemaformulario.dto.RespostaEnvioDTO; // <--- Importa o DTO novo
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

    // GET: Carrega o Formulário para a tela
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");
        String turmaIdStr = req.getParameter("turmaId");
        String formularioIdStr = req.getParameter("formularioId");

        if (aluno == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Long turmaId = Long.parseLong(turmaIdStr);
            Long formularioId = Long.parseLong(formularioIdStr);

            // Verifica se já respondeu para não deixar responder 2 vezes
            if (participacaoService.verificarParticipacao(aluno.getId(), turmaId, formularioId)) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                JsonUtil.sendJson(resp, Map.of("erro", "Você já respondeu este formulário."));
                return;
            }

            // Busca o formulário (Agora com EAGER loading e sem Loop infinito)
            Formulario form = formularioService.buscarPorId(formularioIdStr);
            JsonUtil.sendJson(resp, form);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", "Erro ao carregar dados."));
        }
    }

    // POST: Recebe as respostas do aluno
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (aluno == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            // LÊ O JSON USANDO O DTO NOVO
            RespostaEnvioDTO dto = JsonUtil.readJson(req, RespostaEnvioDTO.class);

            // Converte o DTO para o formato que o Service espera (Map)
            Map<String, String[]> parametros = new HashMap<>();
            parametros.put("turmaId", new String[]{String.valueOf(dto.getTurmaId())});
            parametros.put("formularioId", new String[]{String.valueOf(dto.getFormularioId())});

            if (dto.getRespostas() != null) {
                parametros.putAll(dto.getRespostas());
            }

            // Processa e Salva
            submissaoService.processarEnvio(aluno, parametros);

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Sucesso"));

        } catch (Exception e) {
            e.printStackTrace(); // Mostra erro no console do servidor se houver
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }
}