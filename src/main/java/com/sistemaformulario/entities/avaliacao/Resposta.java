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
@Table(name = "respostas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "submissao_id", nullable = false)
    private Submissao submissao;

    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    @Column(columnDefinition = "TEXT")
    private String textoResposta;


    @ManyToMany
    @JoinTable(
            name = "resposta_selecoes",
            joinColumns = @JoinColumn(name = "resposta_id"),
            inverseJoinColumns = @JoinColumn(name = "alternativa_id")
    )
    private List<Alternativa> alternativasSelecionadas = new ArrayList<>();

    public void adicionarAlternativa(Alternativa alternativa) {
        this.alternativasSelecionadas.add(alternativa);
    }
}