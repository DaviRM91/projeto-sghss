package com.vidaplus.sghss.service;

import com.vidaplus.sghss.dto.ConsultaDTO;
import com.vidaplus.sghss.exception.BusinessException;
import com.vidaplus.sghss.exception.ResourceNotFoundException;
import com.vidaplus.sghss.model.Consulta;
import com.vidaplus.sghss.model.Medico;
import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.ConsultaRepository;
import com.vidaplus.sghss.repository.MedicoRepository;
import com.vidaplus.sghss.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public ConsultaDTO agendar(Long pacienteId, Long medicoId, LocalDateTime dataHora, String observacoes) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado"));

        // Verificar conflito de horário com o médico (30 min antes/depois)
        List<Consulta> conflitos = consultaRepository.findByMedicoIdAndDataHoraBetween(medicoId,
                dataHora.minusMinutes(30), dataHora.plusMinutes(30));
        if (!conflitos.isEmpty()) {
            throw new BusinessException("Horário já ocupado para este médico");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(dataHora);
        consulta.setStatus("AGENDADA");
        consulta.setObservacoes(observacoes);
        Consulta salva = consultaRepository.save(consulta);
        return toDTO(salva);
    }

    public List<ConsultaDTO> listarConsultasPorPaciente(Long pacienteId) {
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new ResourceNotFoundException("Paciente não encontrado");
        }
        return consultaRepository.findByPacienteId(pacienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void cancelar(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));
        consulta.setStatus("CANCELADA");
        consultaRepository.save(consulta);
    }

    private ConsultaDTO toDTO(Consulta c) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(c.getId());
        dto.setPacienteId(c.getPaciente().getId());
        dto.setPacienteNome(c.getPaciente().getNome());
        dto.setMedicoId(c.getMedico().getId());
        dto.setMedicoNome(c.getMedico().getNome());
        dto.setDataHora(c.getDataHora());
        dto.setStatus(c.getStatus());
        dto.setObservacoes(c.getObservacoes());
        return dto;
    }
}
