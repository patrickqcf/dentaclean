package org.dentaclean.service;

import lombok.AllArgsConstructor;
import org.dentaclean.entity.JornadaTrabalho;
import org.dentaclean.repository.JornadaTrabalhoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JornadaTrabalhoService {

    private JornadaTrabalhoRepository repository;

    public JornadaTrabalho craete(JornadaTrabalho obj) {
        existeJornadaTrabalho(obj);
        return repository.save(obj);
    }

    public JornadaTrabalho update(Long id, JornadaTrabalho obj) {
        findById(id);
        obj.setId(id);
        return craete(obj);
    }

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

    private void existeJornadaTrabalho(JornadaTrabalho obj) {
        Optional<JornadaTrabalho> existed = repository.existeJornadaTrabalho(obj.getDentistaId(), obj.getDiaSemana(),
                obj.getHoraInicio(), obj.getHoraFim());
        if (existed.isPresent() && !existed.get().getId().equals(obj.getId())) {
            throw new DataIntegrityViolationException("Jornada de trabalho já cadastrada");
        }
    }

}
