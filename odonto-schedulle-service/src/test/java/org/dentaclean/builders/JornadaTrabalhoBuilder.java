package org.dentaclean.builders;

import org.dentaclean.entity.JornadaTrabalho;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public class JornadaTrabalhoBuilder {

    private JornadaTrabalho jornadaTrabalho;

    @NotNull
    private String uuid = UUID.randomUUID().toString();

    private JornadaTrabalhoBuilder() {
    }

    public static JornadaTrabalhoBuilder umJornadaTrabalhoBuilder() {
        JornadaTrabalhoBuilder builder = new JornadaTrabalhoBuilder();
        builder.jornadaTrabalho = new JornadaTrabalho();
        builder.jornadaTrabalho.setId(1L);
        builder.jornadaTrabalho.setDentistaId(2L);
        builder.jornadaTrabalho.setClinicaId(1L);
        builder.jornadaTrabalho.setDiaSemana(0);
        builder.jornadaTrabalho.setHoraInicio(LocalTime.of(8, 0));
        builder.jornadaTrabalho.setHoraFim(LocalTime.of(12, 0));
        return builder;
    }

    public JornadaTrabalho agora() {
        return jornadaTrabalho;
    }

    public JornadaTrabalhoBuilder validationIdUpdate() {
        this.jornadaTrabalho.setId(5L);
        return this;
    }

    public Optional<JornadaTrabalho> optionalTrabalho() {
        return Optional.of(umJornadaTrabalhoBuilder().agora());
    }

    public Optional<JornadaTrabalho> jornadaTrabalhoExistente() {

        jornadaTrabalho = new JornadaTrabalho(2L, uuid, 1L, 1L, 0,
                LocalTime.of(8, 0), LocalTime.of(12, 0));
        return Optional.of(jornadaTrabalho);
    }

    public JornadaTrabalho jornadaTrabalhoEditada() {
        jornadaTrabalho = new JornadaTrabalho(1L, uuid, 2L, 2L, 7,
                LocalTime.of(14, 0), LocalTime.of(17, 0));
        return jornadaTrabalho;
    }


}