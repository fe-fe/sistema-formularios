package com.sistemaformulario.entities.avaliacao;

import com.sistemaformulario.entities.acesso.Perfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "formularios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private boolean anonimo;

    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    private ProcessoAvaliativo processoAvaliativo;

    @ManyToOne
    @JoinColumn(name = "perfil_alvo_id")
    private Perfil perfilDestino;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL)
    private List<Questao> questoes;

}