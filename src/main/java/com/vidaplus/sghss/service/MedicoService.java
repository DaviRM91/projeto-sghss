package com.vidaplus.sghss.service;

import com.vidaplus.sghss.exception.ResourceNotFoundException;
import com.vidaplus.sghss.model.Medico;
import com.vidaplus.sghss.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    // Listar todos os médicos
    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    // Buscar médico por ID
    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id: " + id));
    }

    // Salvar (cadastrar ou atualizar)
    public Medico salvar(Medico medico) {
        return medicoRepository.save(medico);
    }

    // Deletar médico
    public void deletar(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Médico não encontrado com id: " + id);
        }
        medicoRepository.deleteById(id);
    }
}


