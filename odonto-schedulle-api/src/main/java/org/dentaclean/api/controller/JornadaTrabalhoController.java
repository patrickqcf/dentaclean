package org.dentaclean.api.controller;


import lombok.AllArgsConstructor;
import org.dentaclean.api.dtos.JornadaTrabalhoDTO;
import org.dentaclean.domain.model.JornadaTrabalho;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jornada-trabalho")
@AllArgsConstructor
public class JornadaTrabalhoController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JornadaTrabalho salvar(@Valid @RequestBody JornadaTrabalhoDTO obj) {
        return null;
    }


}