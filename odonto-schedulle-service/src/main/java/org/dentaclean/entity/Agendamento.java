package org.dentaclean.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Table(name = "agendamento")
@Data
public class Agendamento {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "dentista_id", nullable = false)
   private Long dentistaId;
   @Column(name = "cliente_id", nullable = false)
   private Long clientId;
   @Column(name = "data_agendamento", nullable = false)
   private LocalDate dataAgendamento;
   @Column(name = "hora_inicio", nullable = false)
   private LocalDateTime horaInicio;
   @Column(name = "hora_fim", nullable = false)
   private LocalDateTime horaFim;
   @ManyToOne
   @JoinColumn(name = "status_id", nullable = false)
   private Status status;
   @ManyToOne
   @JoinColumn(name = "agendamento_id")
   private Agendamento agendamentoId;

}
