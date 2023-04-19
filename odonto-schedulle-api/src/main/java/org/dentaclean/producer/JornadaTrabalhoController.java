package org.dentaclean.producer;


import lombok.AllArgsConstructor;
import org.dentaclean.response.JornadaTrabalho;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jornada-trabalho")
@AllArgsConstructor
public class JornadaTrabalhoController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JornadaTrabalho salvar(@Valid @RequestBody JornadaTrabalho obj) {
        return null;
    }

    @PutMapping("/{id}")
    public JornadaTrabalho update(@PathVariable Long id, @Valid @RequestBody JornadaTrabalho obj) {
        return null;
    }

    @GetMapping("/{id}")
    public JornadaTrabalho findById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{dentistaId}/dentista")
    public List<JornadaTrabalho> findByDentistaId(@PathVariable Long dentistaId) {
        return null;
    }

}