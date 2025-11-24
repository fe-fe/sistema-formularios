package com.sistemaformulario.dto;

import com.sistemaformulario.entities.enums.TipoQuestao;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QuestaoDTO {
    private String enunciado;
    private TipoQuestao tipo; // MULTIPLA_ESCOLHA, OBJETIVA, ABERTA
    private boolean obrigatoria;
    private List<AlternativaDTO> alternativas; // Pode ser null se for questão aberta
}