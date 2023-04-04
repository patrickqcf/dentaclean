package org.dentaclean.repository;

import org.dentaclean.entity.JornadaTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JornadaTrabalhoRepository extends JpaRepository<JornadaTrabalho, Long> {

    @Query(value = "select jt.* \n" +
            "\tfrom jornada_trabalho jt \n" +
            "\t\twhere jt.dentista_id = :dentistaId \n" +
            "\t\tand jt.dia_semana = :diaSemana\n" +
            "\t\tand ((:horaInicio >= jt.hora_inicio and :horaInicio <= jt.hora_fim) \n" +
            "\t\t\tor (:horaFim >= jt.hora_inicio and :horaFim <= jt.hora_fim)\n" +
            "\t\t)", nativeQuery = true)
    Optional<JornadaTrabalho> existeJornadaTrabalho(@Param("dentistaId") Long dentistaID, @Param("diaSemana") Integer diaSemana,
                                                         @Param("horaInicio") LocalTime horaInicio, @Param("horaFim") LocalTime horaFim);

    List<JornadaTrabalho> findAllByDentistaId(Long dentistaId);
}
