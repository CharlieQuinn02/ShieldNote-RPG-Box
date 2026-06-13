package br.ifg.urt.shieldnoterpgbox.assembler;

import br.ifg.urt.shieldnoterpgbox.controller.SpellSlotController;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellSlotResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class SpellSlotModelAssembler implements RepresentationModelAssembler<SpellSlotResponse, EntityModel<SpellSlotResponse>> {
    @Override
    public EntityModel<SpellSlotResponse> toModel(SpellSlotResponse r) {
        return EntityModel.of(r,
                linkTo(methodOn(SpellSlotController.class).buscarPorSpellCasting(null)).withRel("slots"));
    }
}

