package com.vidaplus.sghss.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", unique = true)
    private Paciente paciente;

    private String historicoClinico;
    private String alergias;
    private String medicamentosContinuos;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private LocalDateTime dataUltimaAtualizacao;
}


