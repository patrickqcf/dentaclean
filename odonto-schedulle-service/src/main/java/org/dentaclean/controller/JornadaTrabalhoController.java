package org.dentaclean.controller;

import lombok.AllArgsConstructor;
import org.dentaclean.entity.JornadaTrabalho;
import org.dentaclean.service.JornadaTrabalhoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jornada-trabalho")
@AllArgsConstructor
public class JornadaTrabalhoController {

    private JornadaTrabalhoService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JornadaTrabalho salvar(@Valid @RequestBody JornadaTrabalho obj) {
        return service.craete(obj);
    }

    @PutMapping("/{id}")
    public JornadaTrabalho update(@PathVariable Long id, @Valid @RequestBody JornadaTrabalho obj) {
        return service.update(id, obj);
    }

    @GetMapping("/{id}")
    public JornadaTrabalho findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{dentistaId}/dentista")
    public List<JornadaTrabalho> findByDentistaId(@PathVariable Long dentistaId) {
        return service.findByDentistaId(dentistaId);
    }

}