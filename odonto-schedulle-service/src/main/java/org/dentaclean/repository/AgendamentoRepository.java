package org.dentaclean.repository;

import org.dentaclean.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    @Query(value = "select case when agendamento = 0 and jornada = 1 then 0 else 1 end as valido\n" +
            "from (\n" +
            "\tselect count(1) as agendamento,\n" +
            "\t(select count(1) as jornada from jornada_trabalho jt \n" +
            "\t\twhere :horaInicio >= jt.hora_inicio  \n" +
            "\t\tAND :horaFim <= jt.hora_fim\n" +
            "\t\tand jt.dentista_id = :dentistaId \n" +
            "\t\tand jt.dia_semana = :diaSemana\n" +
            "\t) as jornada\n" +
            "\tfrom agendamento a\n" +
            "\twhere a.dentista_id = :dentistaId\n" +
            "\tand a.data_agendamento = :dataAgendamento\n" +
            "\tand (:horaInicio < a.hora_fim and :horaFim > a.hora_inicio)\n" +
            "\tand a.status = 'CRIADO'\n" +
            ")v", nativeQuery = true)
    Integer existeAgendamento(@Param("dentistaId") Long dentistaID, @Param("diaSemana") Integer diaSemana,
                                                @Param("dataAgendamento") LocalDate dataAgendamento, @Param("horaInicio") LocalTime horaInicio,
                                                @Param("horaFim") LocalTime horaFim);
}
