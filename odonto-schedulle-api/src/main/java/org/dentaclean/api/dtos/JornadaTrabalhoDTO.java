package org.dentaclean.api.dtos;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class JornadaTrabalhoDTO {

    private Long id;
    private String uuid;
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
