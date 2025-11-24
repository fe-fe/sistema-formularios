package com.sistemaformulario.service;

import com.sistemaformulario.dao.ProcessoAvaliativoDAO;
import com.sistemaformulario.entities.avaliacao.ProcessoAvaliativo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProcessoAvaliativoService {

    private ProcessoAvaliativoDAO processoDAO = new ProcessoAvaliativoDAO();

    public List<ProcessoAvaliativo> listarTodos() {
        return processoDAO.findAll();
    }

    public void criar(String descricao, String dataInicioStr, String dataFimStr) {
        ProcessoAvaliativo p = new ProcessoAvaliativo();
        p.setDescricao(descricao);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        if (dataInicioStr != null && !dataInicioStr.isEmpty())
            p.setDataInicio(LocalDateTime.parse(dataInicioStr, formatter));

        if (dataFimStr != null && !dataFimStr.isEmpty())
            p.setDataFim(LocalDateTime.parse(dataFimStr, formatter));

        processoDAO.create(p);
    }
}