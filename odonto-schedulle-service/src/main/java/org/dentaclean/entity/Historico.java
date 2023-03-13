package org.dentaclean.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Table(name = "historico")
@Data
public class Historico {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @ManyToOne
   @JoinColumn(name = "agendamento_id", nullable = false)
   private Agendamento agendamento;
   @Column(nullable = false)
   private LocalDateTime data;
   @ManyToOne
   @JoinColumn(name = "status_id", nullable = false)
   private Status status;

}
