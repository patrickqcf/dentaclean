package org.dentaclean.service;

import lombok.AllArgsConstructor;
import org.dentaclean.entity.Status;
import org.dentaclean.repository.StatusRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService {

   private StatusRepository repository;

   public Status salvar(Status status) {
      return repository.save(status);
   }

   public Status alterar(Long id, Status status) {
      Status stOld = buscarPorId(id);
      status.setId(stOld.getId());
      return repository.save(status);
   }

   public Status buscarPorId(Long id) {
      Optional<Status> statusOptional = repository.findById(id);
      if(statusOptional.isPresent()){
         return statusOptional.get();
      }
      throw new EmptyResultDataAccessException(1);
   }

   public List<Status> buscarPorDescricao(String descricao) {
      return repository.findByDescricaoContaining(descricao);
   }
}
