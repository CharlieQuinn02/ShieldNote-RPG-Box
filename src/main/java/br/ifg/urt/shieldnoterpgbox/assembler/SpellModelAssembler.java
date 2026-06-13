package br.ifg.urt.shieldnoterpgbox.assembler;

import br.ifg.urt.shieldnoterpgbox.controller.SpellController;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResumoResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class SpellModelAssembler implements RepresentationModelAssembler<SpellResponse, EntityModel<SpellResponse>> {
    @Override
    public EntityModel<SpellResponse> toModel(SpellResponse r) {
        return EntityModel.of(r,
                linkTo(methodOn(SpellController.class).buscarPorId(r.id(), null, null)).withSelfRel(),
                linkTo(methodOn(SpellController.class).listar(null, null, null, null)).withRel("spells"));
    }
    public CollectionModel<EntityModel<SpellResumoResponse>> toResumoCollection(List<SpellResumoResponse> list) {
        List<EntityModel<SpellResumoResponse>> models = list.stream()
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(SpellController.class).buscarPorId(r.id(), null, null)).withSelfRel()))
                .toList();
        return CollectionModel.of(models,
                linkTo(methodOn(SpellController.class).listar(null, null, null, null)).withSelfRel());
    }
}

