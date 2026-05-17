package br.ifg.urt.shieldnoterpgbox.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ifg.urt.shieldnoterpgbox.dto.request.NotesRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;
import br.ifg.urt.shieldnoterpgbox.model.Notes;

@Mapper(componentModel = "spring")
public interface NotesMapper {

    NotesResponseDTO toResponseDTO(Notes notes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Notes toEntity(NotesRequestDTO dto);
    
    List<NotesResponseDTO> toResponseDTOList(List<Notes> notesList);
}