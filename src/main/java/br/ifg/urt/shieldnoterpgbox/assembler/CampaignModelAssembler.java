package br.ifg.urt.shieldnoterpgbox.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.ifg.urt.shieldnoterpgbox.controller.CampaignController;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;

@Component
public class CampaignModelAssembler implements RepresentationModelAssembler<CampaignResponseDTO, EntityModel<CampaignResponseDTO>> {

    @Override
    public EntityModel<CampaignResponseDTO> toModel(CampaignResponseDTO dto) {
        // Cria o envelope HATEOAS no DTO
        EntityModel<CampaignResponseDTO> model = EntityModel.of(dto);

        // Link de identidade (self)
        model.add(linkTo(methodOn(CampaignController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        String status = dto.status().toString();

        // Máquina de estados baseada nos métodos do agregado
        if ("ATIVA".equals(status)) {
            model.add(linkTo(methodOn(CampaignController.class)
                    .iniciarSessao(dto.id()))
                    .withRel("iniciar-sessao"));

            model.add(linkTo(methodOn(CampaignController.class)
                    .pausar(dto.id()))
                    .withRel("pausar-campanha"));

            model.add(linkTo(methodOn(CampaignController.class)
                    .encerrar(dto.id()))
                    .withRel("encerrar-campanha"));
        } 
        else if ("PAUSADA".equals(status)) {
            
            model.add(linkTo(methodOn(CampaignController.class)
                    .reativar(dto.id()))
                    .withRel("reativar-campanha"));
            
            model.add(linkTo(methodOn(CampaignController.class)
                    .encerrar(dto.id()))
                    .withRel("encerrar-campanha"));
        }

        return model;
    }
}