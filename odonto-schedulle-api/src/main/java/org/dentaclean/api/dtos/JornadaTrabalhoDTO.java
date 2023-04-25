package org.dentaclean.api.dtos;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

@Getter
public class JornadaTrabalhoDTO {

    private UUID id;
    @NotNull
    private Long dentistaId;
    @NotNull
    private Long clinicaId;
    @NotNull
    private Integer diaSemana;
    @NotNull
    private LocalTime horaInicio;
    @NotNull
    private LocalTime horaFim;
}
