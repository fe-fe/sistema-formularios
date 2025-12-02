package com.sistemaformulario.dto;

import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.avaliacao.Formulario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class DashboardItemDTO {
    private Turma turma;
    private List<Formulario> formulariosPendentes;
}