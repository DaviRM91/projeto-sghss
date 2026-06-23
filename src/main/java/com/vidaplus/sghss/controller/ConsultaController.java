package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.dto.ConsultaDTO;
import com.vidaplus.sghss.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/agendar")
    public ResponseEntity<ConsultaDTO> agendar(@RequestParam Long pacienteId,
                                               @RequestParam Long medicoId,
                                               @RequestParam String dataHora,
                                               @RequestParam(required = false) String observacoes) {
        LocalDateTime dt = LocalDateTime.parse(dataHora); // formato ISO: 2025-03-20T14:30:00
        ConsultaDTO dto = consultaService.agendar(pacienteId, medicoId, dt, observacoes);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<ConsultaDTO> listarPorPaciente(@PathVariable Long pacienteId) {
        return consultaService.listarConsultasPorPaciente(pacienteId);
    }

    @PutMapping("/cancelar/{consultaId}")
    public ResponseEntity<Void> cancelar(@PathVariable Long consultaId) {
        consultaService.cancelar(consultaId);
        return ResponseEntity.ok().build();
    }
}
