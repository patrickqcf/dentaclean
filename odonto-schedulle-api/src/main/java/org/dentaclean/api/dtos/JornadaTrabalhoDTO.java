package org.dentaclean.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;


@Entity
@Table(name = "jornada_trabalho")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JornadaTrabalhoDTO {

    @Id
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
