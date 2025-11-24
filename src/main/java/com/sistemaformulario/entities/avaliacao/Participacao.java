package com.sistemaformulario.entities.resposta;

import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.acesso.Usuario;
import com.sistemaformulario.entities.avaliacao.Formulario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "controle_participacao", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aluno_id", "formulario_id", "turma_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "formulario_id", nullable = false)
    private Formulario formulario;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    private LocalDateTime dataParticipacao = LocalDateTime.now();
}