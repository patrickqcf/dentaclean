package org.dentaclean.controller;

import lombok.AllArgsConstructor;
import org.dentaclean.entity.Agendamento;
import org.dentaclean.service.AgendamentoService;
import org.dentaclean.service.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
public class AgendamentoController {

    private AgendamentoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agendamento salvar(@Valid @RequestBody Agendamento obj) {
        return service.create(obj);
    }

    @GetMapping("/{id}")
    public Agendamento findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/remarcar")
    public SuccessResponse remarcar(@Valid @RequestBody Agendamento obj) {
        service.remarcar(obj);
        return SuccessResponse.aply("Registro remarcado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public SuccessResponse cancel(@PathVariable Long id) {
        service.cancel(id);
        return SuccessResponse.aply("Registro cancelado com sucesso.");
    }


}