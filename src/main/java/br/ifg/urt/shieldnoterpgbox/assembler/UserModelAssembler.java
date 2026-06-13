package br.ifg.urt.shieldnoterpgbox.assembler;

import br.ifg.urt.shieldnoterpgbox.controller.UserController;
import br.ifg.urt.shieldnoterpgbox.dto.response.UserResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponse, EntityModel<UserResponse>> {
    @Override
    public EntityModel<UserResponse> toModel(UserResponse r) {
        return EntityModel.of(r,
                linkTo(methodOn(UserController.class).buscarPorId(r.id())).withSelfRel(),
                linkTo(methodOn(UserController.class).listarTodos()).withRel("users"));
    }
}