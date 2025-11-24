package com.sistemaformulario.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class FormularioCreationDTO {
    private String titulo;
    private boolean anonimo;
    private Long processoId;
    private Long perfilDestinoId;
    private List<QuestaoDTO> questoes;
}