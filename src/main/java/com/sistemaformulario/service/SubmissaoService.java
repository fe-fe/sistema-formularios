package com.sistemaformulario.service;

import com.sistemaformulario.dao.*;
import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.entities.avaliacao.Alternativa;
import com.sistemaformulario.entities.avaliacao.Formulario;
import com.sistemaformulario.entities.avaliacao.Questao;
import com.sistemaformulario.entities.enums.TipoQuestao;
import com.sistemaformulario.entities.avaliacao.Submissao;
import com.sistemaformulario.entities.resposta.Participacao;
import com.sistemaformulario.entities.avaliacao.Resposta;

import java.time.LocalDateTime;
import java.util.Map;

public class SubmissaoService {

    // Use os DAOs correspondentes (Renomeie seus DAOs também para SubmissaoDAO e ParticipacaoDAO)
    private SubmissaoDAO submissaoDAO = new SubmissaoDAO();
    private ParticipacaoDAO participacaoDAO = new ParticipacaoDAO();
    private FormularioDAO formularioDAO = new FormularioDAO();
    private TurmaDAO turmaDAO = new TurmaDAO();
    private QuestaoDAO questaoDAO = new QuestaoDAO();
    private AlternativaDAO alternativaDAO = new AlternativaDAO();

    public void processarEnvio(Usuario aluno, Map<String, String[]> parametros) {

        // 1. Validar IDs
        Long turmaId = Long.parseLong(parametros.get("turmaId")[0]);
        Long formularioId = Long.parseLong(parametros.get("formularioId")[0]);

        // 2. Checar Participação (Lista de Presença)
        if (participacaoDAO.hasStudentReplied(aluno.getId(), turmaId, formularioId)) {
            throw new RuntimeException("Participação já registrada para este formulário.");
        }

        Turma turma = turmaDAO.findById(turmaId);
        Formulario formulario = formularioDAO.findById(formularioId);

        // 3. Criar a Submissão (O Envelope)
        Submissao submissao = new Submissao();
        submissao.setTurma(turma);
        submissao.setFormulario(formulario);
        submissao.setDataEnvio(LocalDateTime.now());

        // Lógica do Anonimato
        if (formulario.isAnonimo()) {
            submissao.setAluno(null); // Protege a identidade na submissão
        } else {
            submissao.setAluno(aluno); // Vincula a identidade
        }

        // 4. Processar Respostas
        for (String key : parametros.keySet()) {
            if (key.startsWith("questao_")) {
                String idQuestaoStr = key.substring("questao_".length());
                Long idQuestao = Long.parseLong(idQuestaoStr);
                Questao questao = questaoDAO.findById(idQuestao);

                Resposta resposta = new Resposta();
                resposta.setSubmissao(submissao); // Link com a nova entidade
                resposta.setQuestao(questao);

                String[] valores = parametros.get(key);

                if (questao.getTipo() == TipoQuestao.ABERTA) {
                    if (valores.length > 0) resposta.setTextoResposta(valores[0]);
                } else {
                    for (String idAlt : valores) {
                        Alternativa alt = alternativaDAO.findById(Long.parseLong(idAlt));
                        if (alt != null) resposta.adicionarAlternativa(alt);
                    }
                }
                submissao.getRespostas().add(resposta);
            }
        }

        // 5. Salvar Submissão (Cascata salva respostas)
        submissaoDAO.create(submissao);

        // 6. Registrar Participação (Lista de presença)
        Participacao participacao = new Participacao();
        participacao.setAluno(aluno); // Sempre identificado aqui
        participacao.setTurma(turma);
        participacao.setFormulario(formulario);

        participacaoDAO.create(participacao);
    }
}