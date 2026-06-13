package br.ifg.urt.shieldnoterpgbox.assembler;

import br.ifg.urt.shieldnoterpgbox.controller.SpellCastingController;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellCastingResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class SpellCastingModelAssembler implements RepresentationModelAssembler<SpellCastingResponse, EntityModel<SpellCastingResponse>> {
    @Override
    public EntityModel<SpellCastingResponse> toModel(SpellCastingResponse r) {
        return EntityModel.of(r,
                linkTo(methodOn(SpellCastingController.class).buscarPorId(r.id())).withSelfRel(),
                linkTo(methodOn(SpellCastingController.class).listarTodos()).withRel("spellcastings"));
    }
}
