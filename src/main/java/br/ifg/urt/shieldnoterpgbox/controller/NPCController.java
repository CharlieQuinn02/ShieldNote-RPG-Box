package br.ifg.urt.shieldnoterpgbox.controller;
import br.ifg.urt.shieldnoterpgbox.dto.request.NPCRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.NPCDetailResponse;
import br.ifg.urt.shieldnoterpgbox.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.validation.annotation.Validated;
import java.util.UUID;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/npcs")
@Tag(name = "NPC")
@Validated
public class NPCController {

    private final CharacterService service;

    public NPCController(CharacterService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new NPC")
    @PostMapping
    public ResponseEntity<EntityModel<NPCDetailResponse>> createNPC(@Valid @RequestBody NPCRequest request) {
        NPCDetailResponse response = service.createNpc(request);
        URI location = URI.create("/api/v1/npcs/" + response.id());
        return ResponseEntity.created(location).body(buildEntityModel(response));
    }

    @Operation(summary = "Get NPC by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<NPCDetailResponse>> getNPC(@PathVariable UUID id) {
        NPCDetailResponse response = (NPCDetailResponse) service.getCharacterById(id);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Update NPC by ID")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<NPCDetailResponse>> updateNPC(
            @PathVariable UUID id, @Valid @RequestBody NPCRequest request) {
        NPCDetailResponse response = service.updateNpc(id, request);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Delete NPC by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNPC(@PathVariable UUID id) {
        service.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Apply damage to NPC")
    @PostMapping("/{id}/damage")
    public ResponseEntity<EntityModel<NPCDetailResponse>> damageNPC(
            @PathVariable UUID id, @RequestParam int amount) {
        NPCDetailResponse response = (NPCDetailResponse) service.takeDamage(id, amount);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Heal NPC")
    @PostMapping("/{id}/heal")
    public ResponseEntity<EntityModel<NPCDetailResponse>> healNPC(
            @PathVariable UUID id, @RequestParam int amount) {
        NPCDetailResponse response = (NPCDetailResponse) service.heal(id, amount);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    private EntityModel<NPCDetailResponse> buildEntityModel(NPCDetailResponse dto) {
        Link selfLink = linkTo(methodOn(NPCController.class).getNPC(dto.id())).withSelfRel();
        Link updateLink = linkTo(methodOn(NPCController.class).updateNPC(dto.id(), null)).withRel("update");
        Link deleteLink = linkTo(methodOn(NPCController.class).deleteNPC(dto.id())).withRel("delete");
        Link listLink = linkTo(methodOn(CharacterController.class).getAllCharacters()).withRel("list");
        Link damageLink = linkTo(methodOn(NPCController.class).damageNPC(dto.id(), 0)).withRel("damage");
        Link healLink = linkTo(methodOn(NPCController.class).healNPC(dto.id(), 0)).withRel("heal");
        return EntityModel.of(dto, selfLink, updateLink, deleteLink, listLink, damageLink, healLink);
    }
}

