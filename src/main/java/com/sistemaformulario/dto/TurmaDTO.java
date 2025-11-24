package com.sistemaformulario.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TurmaDTO {
    private String nome;
    private Long disciplinaId;
    private Long professorId;
}