package org.dentaclean.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
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
   @Column(name = "created_at", nullable = false, updatable = false)
   private LocalDateTime createdAt;
   @Column(name = "update_at", nullable = false, updatable = false)
   private LocalDateTime updatedAt;

   @PrePersist
   public void prePresist() {
      createdAt = LocalDateTime.now();
   }
}
