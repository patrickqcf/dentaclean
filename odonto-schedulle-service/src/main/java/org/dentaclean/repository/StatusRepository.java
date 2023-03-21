package org.dentaclean.repository;

import org.dentaclean.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
   @Query(value = "from Status s where lower(s.descricao) like %:descricao%",
           countQuery = "select count(s.id) from Servico s where lower(s.descricao) like %:descricao%")
   List<Status> findByDescricaoContaining(@Param("descricao") String descricao);
}
