package org.dentaclean.service;

import lombok.AllArgsConstructor;
import org.dentaclean.entity.JornadaTrabalho;
import org.dentaclean.repository.JornadaTrabalhoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JornadaTrabalhoService {

   private JornadaTrabalhoRepository repository;

   public JornadaTrabalho salvar(JornadaTrabalho status) {
      return repository.save(status);
   }

   public JornadaTrabalho alterar(Long id, JornadaTrabalho status) {
      JornadaTrabalho stOld = buscarPorId(id);
      status.setId(stOld.getId());
      return repository.save(status);
   }

   public JornadaTrabalho buscarPorId(Long id) {
      Optional<JornadaTrabalho> statusOptional = repository.findById(id);
      if(statusOptional.isPresent()){
         return statusOptional.get();
      }
      throw new EmptyResultDataAccessException(1);
   }

}
