package org.dentaclean.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "jornada_trabalho")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JornadaTrabalho implements Serializable {

    @Id
    private Long id;
    private String uuid;
    @Column(name = "dentista_id")
    private Long dentistaId;
    @Column(name = "clinica_id")
    private Long clinicaId;
    @Column(name = "dia_semana")
    private Integer diaSemana;
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    @Column(name = "hora_fim")
    private LocalTime horaFim;

}
