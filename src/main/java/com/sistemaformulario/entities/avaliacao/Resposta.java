package com.sistemaformulario.entities.avaliacao;

import com.sistemaformulario.entities.avaliacao.Alternativa;
import com.sistemaformulario.entities.avaliacao.Questao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "respostas") // Nome da tabela simplificado
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O "Envelope" (A submissão completa)
    @ManyToOne
    @JoinColumn(name = "submissao_id", nullable = false)
    private Submissao submissao;

    // Qual pergunta foi respondida
    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    // --- CONTEÚDO DA RESPOSTA ---

    // Se for questão ABERTA
    @Column(columnDefinition = "TEXT")
    private String textoResposta;

    // Se for OBJETIVA ou MÚLTIPLA ESCOLHA
    @ManyToMany
    @JoinTable(
            name = "resposta_selecoes", // Tabela auxiliar para guardar os X marcados
            joinColumns = @JoinColumn(name = "resposta_id"),
            inverseJoinColumns = @JoinColumn(name = "alternativa_id")
    )
    private List<Alternativa> alternativasSelecionadas = new ArrayList<>();

    // Método auxiliar para adicionar alternativas na lista
    public void adicionarAlternativa(Alternativa alternativa) {
        this.alternativasSelecionadas.add(alternativa);
    }
}