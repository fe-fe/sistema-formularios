package com.sistemaformulario.controller.admin;

import com.sistemaformulario.dto.CursoDTO;
import com.sistemaformulario.dto.DisciplinaDTO;
import com.sistemaformulario.dto.ProfessorDTO;
import com.sistemaformulario.service.CursoService;
import com.sistemaformulario.service.DisciplinaService;
import com.sistemaformulario.service.UsuarioService;
import com.sistemaformulario.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

// Mapeamos várias URLs para o mesmo Servlet para simplificar
@WebServlet(urlPatterns = {"/api/admin/cursos", "/api/admin/disciplinas", "/api/admin/professores"})
public class AdminDadosAcademicosServlet extends HttpServlet {

    private CursoService cursoService = new CursoService();
    private DisciplinaService disciplinaService = new DisciplinaService();
    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.contains("/cursos")) {
            JsonUtil.sendJson(resp, cursoService.listar());
        } else if (path.contains("/disciplinas")) {
            JsonUtil.sendJson(resp, disciplinaService.listar());
        }
        // Adicionar listagem de professores se necessário
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        try {
            if (path.contains("/cursos")) {
                CursoDTO dto = JsonUtil.readJson(req, CursoDTO.class);
                cursoService.criar(dto);
            }
            else if (path.contains("/disciplinas")) {
                DisciplinaDTO dto = JsonUtil.readJson(req, DisciplinaDTO.class);
                disciplinaService.criar(dto);
            }
            else if (path.contains("/professores")) {
                ProfessorDTO dto = JsonUtil.readJson(req, ProfessorDTO.class);
                usuarioService.cadastrarProfessor(dto.getNome(), dto.getEmail(), dto.getSenha());
            }

            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.sendJson(resp, Map.of("mensagem", "Cadastrado com sucesso"));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJson(resp, Map.of("erro", e.getMessage()));
        }
    }
}