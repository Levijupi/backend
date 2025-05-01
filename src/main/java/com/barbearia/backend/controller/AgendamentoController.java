package com.barbearia.backend.controller;

import com.barbearia.backend.model.Agendamento;
import com.barbearia.backend.repository.AgendamentoRepository;
import com.barbearia.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") 
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/agendar")
    public Agendamento criar(@RequestBody Agendamento agendamento) {
        Agendamento salvo = repository.save(agendamento);

        // Envia o e-mail se o campo email for preenchido
        if (agendamento.getEmail() != null && !agendamento.getEmail().isEmpty()) {
            String corpoHtml = String.format(
                "<p>Olá %s, seu agendamento foi concluído no dia <b>%s</b> às <b>%s</b> com o barbeiro <b>%s</b>.</p>" +
                "<p>Segue o serviço agendado:</p>" +
                "<ul><li>%s</li></ul>" +
                "<p>O código do seu agendamento é: <strong>%d</strong></p>" +
                "<p>Para cancelar, acesse <a href=\"https://web-barber-phi.vercel.app/cancelar-agendamento\">Cancelar Agendamento</a> e insira o código.</p>" +
                "<p>A barbearia Ramos agradece a preferência. Venha ficar novo de novo!</p>",
                agendamento.getNomeCliente(),
                agendamento.getDataAgendamento(),
                agendamento.getHorario(),
                agendamento.getBarbeiro(),
                agendamento.getServico(),
                salvo.getId()
            );

            emailService.enviarEmail(
                agendamento.getEmail(),
                "Agendamento Confirmado!",
                corpoHtml
            );
        }


        return salvo;
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
