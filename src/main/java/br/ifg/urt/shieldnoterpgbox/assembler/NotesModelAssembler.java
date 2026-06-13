package br.ifg.urt.shieldnoterpgbox.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.ifg.urt.shieldnoterpgbox.controller.NotesController;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;

@Component
public class NotesModelAssembler implements RepresentationModelAssembler<NotesResponseDTO, EntityModel<NotesResponseDTO>> {

    @Override
    public EntityModel<NotesResponseDTO> toModel(NotesResponseDTO dto) {
        // Envolve o DTO em um envelope de modelo de representação
        EntityModel<NotesResponseDTO> model = EntityModel.of(dto);

        // Link 'self': Aponta para a rota de consulta individual deste post-it específico
        model.add(linkTo(methodOn(NotesController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        // Link 'alternar-fixacao': Fornece a rota direta para fixar/soltar o post-it
        model.add(linkTo(methodOn(NotesController.class)
                .alternarFixacao(dto.id()))
                .withRel("alternar-fixacao"));

        // Link 'deletar': Fornece a rota de exclusão da nota
        model.add(linkTo(methodOn(NotesController.class)
                .deletar(dto.id()))
                .withRel("deletar"));

        return model;
    }
}