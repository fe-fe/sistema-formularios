package com.sistemaformulario.entities.avaliacao;

import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.entities.avaliacao.Formulario;
import com.sistemaformulario.entities.avaliacao.Resposta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "submissoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Submissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataEnvio = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "formulario_id", nullable = false)
    private Formulario formulario;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Usuario aluno;

    @OneToMany(mappedBy = "submissao", cascade = CascadeType.ALL)
    private List<Resposta> respostas = new ArrayList<>();
}