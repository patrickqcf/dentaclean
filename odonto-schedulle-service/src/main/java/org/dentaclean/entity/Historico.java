package org.dentaclean.entity;

import lombok.Data;
import org.dentaclean.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico")
@Data
public class Historico {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   @ManyToOne
   @JoinColumn(name = "agendamento_id", nullable = false)
   private Agendamento agendamento;

   @NotNull
   @Column(nullable = false)
   private LocalDateTime data;

   @NotNull
   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private Status status;

}
