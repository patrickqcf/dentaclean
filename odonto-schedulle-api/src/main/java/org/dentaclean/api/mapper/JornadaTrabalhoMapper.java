package org.dentaclean.api.mapper;

import lombok.AllArgsConstructor;
import org.dentaclean.api.dtos.JornadaTrabalhoDTO;
import org.dentaclean.domain.model.JornadaTrabalho;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class JornadaTrabalhoMapper {

    private ModelMapper modelMapper;

    public List<JornadaTrabalhoDTO> mapToListDto(List entities) {
        return modelMapper.map(entities, List.class);
    }

    public JornadaTrabalhoDTO mapToDto(JornadaTrabalho entity) {
        return modelMapper.map(entity, JornadaTrabalhoDTO.class);
    }

    public JornadaTrabalho mapToEntity(JornadaTrabalhoDTO dto) {
        return modelMapper.map(dto, JornadaTrabalho.class);
    }
}
