package com.sistemaformulario.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class RespostaEnvioDTO {
    private Long turmaId;
    private Long formularioId;
    // O mapa recebe: "questao_1" -> ["Resposta"]
    private Map<String, String[]> respostas;
}