package com.sistemaformulario.entities.avaliacao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "processos_avaliativos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoAvaliativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @OneToMany(mappedBy = "processoAvaliativo")
    private List<Formulario> formularios;

}