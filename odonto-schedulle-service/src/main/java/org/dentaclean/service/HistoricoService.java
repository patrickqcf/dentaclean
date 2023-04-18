package org.dentaclean.service;

import lombok.AllArgsConstructor;
import org.dentaclean.entity.Historico;
import org.dentaclean.repository.HistoricoRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoricoService {

    private HistoricoRepository repository;

    public Historico create(Historico obj) {
        return repository.save(obj);
    }

}
