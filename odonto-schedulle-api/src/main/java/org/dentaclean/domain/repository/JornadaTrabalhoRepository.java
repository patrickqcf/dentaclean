package org.dentaclean.domain.repository;

import org.dentaclean.domain.model.JornadaTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JornadaTrabalhoRepository extends JpaRepository<JornadaTrabalho, Long> {
    List<JornadaTrabalho> findAllByDentistaId(Long dentistaID);
}
