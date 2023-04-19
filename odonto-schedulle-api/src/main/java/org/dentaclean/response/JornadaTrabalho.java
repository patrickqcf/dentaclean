package org.dentaclean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JornadaTrabalho {

    @Id
    private Long id;
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
