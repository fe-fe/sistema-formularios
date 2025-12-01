package com.sistemaformulario.entities.avaliacao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alternativas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alternativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    private Integer peso;

    @ManyToOne
    @JoinColumn(name = "questao_id")
    @JsonIgnore
    private Questao questao;

}