package org.dentaclean.entity;

import lombok.Data;
import org.dentaclean.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
@Data
public class Agendamento {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   @Column(name = "dentista_id", nullable = false)
   private Long dentistaId;

   @NotNull
   @Column(name = "cliente_id", nullable = false)
   private Long clientId;

   @NotNull
   @Column(name = "data_agendamento", nullable = false)
   private LocalDate dataAgendamento;

   @NotNull
   @Column(name = "hora_inicio", nullable = false)
   private LocalTime horaInicio;

   @NotNull
   @Column(name = "hora_fim", nullable = false)
   private LocalTime horaFim;


   @NotNull
   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private Status status;

   @ManyToOne
   @JoinColumn(name = "agendamento_id")
   private Agendamento agendamentoId;

}
