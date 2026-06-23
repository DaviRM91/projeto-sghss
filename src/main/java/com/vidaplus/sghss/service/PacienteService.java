package com.vidaplus.sghss.service;

import com.vidaplus.sghss.dto.PacienteDTO;
import com.vidaplus.sghss.dto.PacienteRequestDTO;
import com.vidaplus.sghss.exception.BusinessException;
import com.vidaplus.sghss.exception.ResourceNotFoundException;
import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public PacienteDTO cadastrar(PacienteRequestDTO request) {
        if (pacienteRepository.findByCpf(request.getCpf()).isPresent()) {
            throw new BusinessException("CPF já cadastrado");
        }
        Paciente paciente = new Paciente();
        paciente.setNome(request.getNome());
        paciente.setCpf(request.getCpf());
        paciente.setDataNascimento(request.getDataNascimento());
        paciente.setEmail(request.getEmail());
        paciente.setTelefone(request.getTelefone());
        paciente.setEndereco(request.getEndereco());
        Paciente salvo = pacienteRepository.save(paciente);
        return toDTO(salvo);
    }

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        return toDTO(paciente);
    }

    public PacienteDTO atualizar(Long id, PacienteRequestDTO request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        paciente.setNome(request.getNome());
        paciente.setEmail(request.getEmail());
        paciente.setTelefone(request.getTelefone());
        paciente.setEndereco(request.getEndereco());
        // CPF não deve ser alterado
        return toDTO(pacienteRepository.save(paciente));
    }

    public void deletar(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente não encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    private PacienteDTO toDTO(Paciente p) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setCpf(p.getCpf());
        dto.setDataNascimento(p.getDataNascimento());
        dto.setEmail(p.getEmail());
        dto.setTelefone(p.getTelefone());
        dto.setEndereco(p.getEndereco());
        return dto;
    }
}