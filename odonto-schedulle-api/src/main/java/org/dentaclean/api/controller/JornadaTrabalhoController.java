package org.dentaclean.api.controller;


import lombok.AllArgsConstructor;
import org.dentaclean.api.dtos.JornadaTrabalhoDTO;
import org.dentaclean.domain.service.JornadaTrabalhoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jornada-trabalho")
@AllArgsConstructor
public class JornadaTrabalhoController {

    private final JornadaTrabalhoService jornadaTrabalhoService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SuccessResponse salvar(@Valid @RequestBody JornadaTrabalhoDTO obj) {
        return jornadaTrabalhoService.sendJornadaTrabalho(obj);
    }


}