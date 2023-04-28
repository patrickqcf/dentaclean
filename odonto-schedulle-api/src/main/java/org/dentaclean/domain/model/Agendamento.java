package org.dentaclean.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dentaclean.domain.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento {

    @Id
    private Long id;
    private String uuid;
    @Column(name = "dentista_id")
    private Long dentistaId;
    @Column(name = "cliente_id")
    private Long clienteId;
    @Column(name = "data_agendamento")
    private LocalDate dataAgendamento;
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    @Column(name = "hora_fim")
    private LocalTime horaFim;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamentoId;


}
