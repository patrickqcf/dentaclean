package org.dentaclean.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
