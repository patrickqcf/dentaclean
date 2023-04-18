package org.dentaclean.builders;

import org.dentaclean.entity.Agendamento;
import org.dentaclean.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class AgendamentoBuilder {

    private Agendamento agendamento;

    private AgendamentoBuilder() {
    }

    public static AgendamentoBuilder umAgendamentoBuilder() {
        AgendamentoBuilder builder = new AgendamentoBuilder();
        builder.agendamento = new Agendamento();
        builder.agendamento.setId(1L);
        builder.agendamento.setStatus(Status.CRIADO);
        builder.agendamento.setDentistaId(1L);
        builder.agendamento.setClienteId(1L);
        builder.agendamento.setDataAgendamento(LocalDate.of(2023, 03, 13));
        builder.agendamento.setHoraInicio(LocalTime.of(8, 0));
        builder.agendamento.setHoraFim(LocalTime.of(11, 0));
        builder.agendamento.setAgendamentoId(null);
        return builder;
    }

    public Agendamento agora() {
        return agendamento;
    }

    public Optional<Agendamento> optionalAgendamento() {
        return Optional.of(umAgendamentoBuilder().agora());
    }

    public Optional<Agendamento> agendamentoCancel() {
        return Optional.of(cancelado());
    }

    public Agendamento cancelado() {
        agendamento.setStatus(Status.CANCELADO);
        agendamento.setAgendamentoId(agendamento);
        return agendamento;
    }

    public AgendamentoBuilder comStatus(Status status) {
        agendamento.setStatus(status);
        return this;
    }
}
