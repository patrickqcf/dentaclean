package org.dentaclean.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "jornada_trabalho")
@Data
public class JornadaTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dentista_id", nullable = false)
    private Long dentistaId;

    @Column(name = "clinica_id", nullable = false)
    private Long clinicaId;

    @Column(name = "dia_semana")
    private int diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

}
