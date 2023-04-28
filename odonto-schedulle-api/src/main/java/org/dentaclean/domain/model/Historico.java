package org.dentaclean.domain.model;

import lombok.Data;
import org.dentaclean.domain.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico")
@Data
public class Historico {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private Status status;

}
