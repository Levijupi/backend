package com.barbearia.backend.controller;

import com.barbearia.backend.model.Agendamento;
import com.barbearia.backend.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") 
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository repository;

    @PostMapping("/agendar")
    public Agendamento criar(@RequestBody Agendamento agendamento) {
        return repository.save(agendamento);
    }

    @DeleteMapping("/cancelar-agendamento/{id}")
    public String cancelar(@PathVariable Long id) {
        Optional<Agendamento> agendamento = repository.findById(id);
        if (agendamento.isPresent()) {
            repository.deleteById(id);
            return "{\"message\":\"Agendamento cancelado com sucesso.\"}";
        } else {
            return "{\"message\":\"Agendamento não encontrado.\"}";
        }
    }
}
