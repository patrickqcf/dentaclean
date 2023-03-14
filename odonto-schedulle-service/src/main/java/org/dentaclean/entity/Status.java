package org.dentaclean.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "status")
@Data
public class Status {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(length = 30, nullable = false, unique = true)
   private String descricao;
}
