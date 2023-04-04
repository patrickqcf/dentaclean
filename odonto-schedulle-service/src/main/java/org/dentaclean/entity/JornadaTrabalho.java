package org.dentaclean.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Table(name = "jornada_trabalho")
@Data
public class JornadaTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dentista_id", nullable = false)
    private Long dentistaId;

    @NotNull
    @Column(name = "clinica_id", nullable = false)
    private Long clinicaId;

    @NotNull
    @Column(name = "dia_semana")
    private Integer diaSemana;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

}
