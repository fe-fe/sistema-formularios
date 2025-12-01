package com.sistemaformulario.entities.avaliacao;

import com.sistemaformulario.entities.enums.TipoQuestao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "questoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Enumerated(EnumType.STRING)
    private TipoQuestao tipo;

    private boolean obrigatoria;

    @ManyToOne
    @JoinColumn(name = "formulario_id")
    @JsonIgnore
    private Formulario formulario;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Alternativa> alternativas;

}

