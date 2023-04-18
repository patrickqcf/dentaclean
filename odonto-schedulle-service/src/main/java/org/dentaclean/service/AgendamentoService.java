package org.dentaclean.service;

import lombok.AllArgsConstructor;
import org.dentaclean.entity.Agendamento;
import org.dentaclean.entity.Historico;
import org.dentaclean.enums.Status;
import org.dentaclean.exceptions.NegocioException;
import org.dentaclean.repository.AgendamentoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private AgendamentoRepository repository;
    private HistoricoService historicoService;

    @Transactional
    public Agendamento create(Agendamento obj) {
        existeAgendamento(obj);
        Agendamento agendamento = repository.save(obj);
        createHistorico(agendamento);
        return agendamento;
    }

    public Agendamento findById(Long id) {
        Optional<Agendamento> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EmptyResultDataAccessException(0);
    }

    @Transactional
    public Agendamento cancel(Long id) {
        Agendamento agendamento = findById(id);
        if (agendamento.getStatus() == Status.CRIADO) {
            agendamento.setStatus(Status.CANCELADO);
            agendamento.setAgendamentoId(agendamento);
            createHistorico(agendamento);
            return repository.save(agendamento);
        }
        throw new NegocioException("Agendamento já foi cancelado ou finalizado anteriormente.");
    }

    @Transactional
    public Agendamento remarcar(Long id, Agendamento obj) {
        cancel(id);
        obj.setId(null);
        Agendamento agendamento = create(obj);
        return agendamento;
    }

    private void existeAgendamento(Agendamento obj) {
        Integer existed = repository.existeAgendamento(obj.getDentistaId(), obj.getDataAgendamento().getDayOfWeek().getValue(),
                obj.getDataAgendamento(), obj.getHoraInicio(), obj.getHoraFim());
        if (existed > 0) {
            throw new DataIntegrityViolationException("Agendamento indisponível.");
        }
    }

    private void createHistorico(Agendamento obj) {
        Historico historico = new Historico();
        historico.setAgendamento(obj);
        historico.setData(LocalDateTime.now());
        historico.setStatus(obj.getStatus());
        historicoService.create(historico);
    }

}
