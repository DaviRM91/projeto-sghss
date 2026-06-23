package com.vidaplus.sghss.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultaDTO {

    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private Long medicoId;
    private String medicoNome;
    private LocalDateTime dataHora;
    private String status;
    private String observacoes;
}


