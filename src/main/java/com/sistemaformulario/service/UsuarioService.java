package com.sistemaformulario.service;

import com.sistemaformulario.dao.PerfilDAO;
import com.sistemaformulario.dao.UsuarioDAO;
import com.sistemaformulario.dto.CadastroDTO;
import com.sistemaformulario.entities.acesso.Perfil;
import com.sistemaformulario.entities.acesso.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PerfilDAO perfilDAO = new PerfilDAO();

    public Usuario autenticar(String email, String senhaPura) {
        Usuario usuario = usuarioDAO.findByEmail(email);

        if (usuario != null && BCrypt.checkpw(senhaPura, usuario.getSenha())) {
            return usuario;
        }

        return null;
    }

    public void cadastrarAluno(CadastroDTO dto) {
        if (usuarioDAO.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Perfil perfilAluno = perfilDAO.findByName("ALUNO");
        if (perfilAluno == null) {
            perfilAluno = perfilDAO.findById(4L);
            if (perfilAluno == null) throw new RuntimeException("Perfil de ALUNO não encontrado no banco.");
        }

        Usuario novo = new Usuario();
        novo.setNome(dto.getNome());
        novo.setEmail(dto.getEmail());

        String senhaHash = BCrypt.hashpw(dto.getSenha(), BCrypt.gensalt());
        novo.setSenha(senhaHash);

        novo.setPerfil(perfilAluno);

        usuarioDAO.create(novo);
    }

    public void cadastrarProfessor(String nome, String email, String senhaPura) {
        if (usuarioDAO.findByEmail(email) != null) throw new RuntimeException("Email já existe.");

        Perfil perfilProf = perfilDAO.findByName("PROFESSOR");
        if (perfilProf == null) perfilProf = perfilDAO.findById(2L);

        Usuario prof = new Usuario();
        prof.setNome(nome);
        prof.setEmail(email);

        String senhaHash = BCrypt.hashpw(senhaPura, BCrypt.gensalt());
        prof.setSenha(senhaHash);

        prof.setPerfil(perfilProf);

        usuarioDAO.create(prof);
    }
}