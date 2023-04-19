package org.dentaclean.service;

import lombok.AllArgsConstructor;
import org.dentaclean.repository.JornadaTrabalhoRepository;
import org.dentaclean.response.JornadaTrabalho;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JornadaTrabalhoService {

    private JornadaTrabalhoRepository repository;

    public JornadaTrabalho findById(Long id) {
        Optional<JornadaTrabalho> optionalJornadaTrabalho = repository.findById(id);
        if (optionalJornadaTrabalho.isPresent()) {
            return optionalJornadaTrabalho.get();
        }
        throw new EmptyResultDataAccessException(1);
    }

    public List<JornadaTrabalho> findByDentistaId(Long dentistaID) {
        return repository.findAllByDentistaId(dentistaID);
    }

}
