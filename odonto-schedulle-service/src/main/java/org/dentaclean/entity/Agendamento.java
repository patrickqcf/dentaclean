package org.dentaclean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long clienteId;

    @NotNull
    @Column(name = "data_agendamento", nullable = false)
    private LocalDate dataAgendamento;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamentoId;

    @PrePersist
    public void prePresist() {
        this.status = Status.CRIADO;
    }
}
