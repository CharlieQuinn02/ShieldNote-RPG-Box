package br.ifg.urt.shieldnoterpgbox.controller;
import br.ifg.urt.shieldnoterpgbox.dto.request.PlayerCharacterRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.PlayerCharacterDetailResponse;
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
@RequestMapping("/api/v1/player-characters")
@Tag(name = "PlayerCharacter")
@Validated
public class PlayerCharacterController {

    private final CharacterService service;

    public PlayerCharacterController(CharacterService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new player character")
    @PostMapping
    public ResponseEntity<EntityModel<PlayerCharacterDetailResponse>> createPlayerCharacter(@Valid @RequestBody PlayerCharacterRequest request) {
        PlayerCharacterDetailResponse response = service.createPlayerCharacter(request);
        URI location = URI.create("/api/v1/player-characters/" + response.id());
        return ResponseEntity.created(location).body(buildEntityModel(response));
    }

    @Operation(summary = "Get player character by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PlayerCharacterDetailResponse>> getPlayerCharacter(@PathVariable UUID id) {
        PlayerCharacterDetailResponse response = (PlayerCharacterDetailResponse) service.getCharacterById(id);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Update player character by ID")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PlayerCharacterDetailResponse>> updatePlayerCharacter(
            @PathVariable UUID id, @Valid @RequestBody PlayerCharacterRequest request) {
        PlayerCharacterDetailResponse response = service.updatePlayerCharacter(id, request);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Delete player character by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerCharacter(@PathVariable UUID id) {
        service.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Apply damage to player character")
    @PostMapping("/{id}/damage")
    public ResponseEntity<EntityModel<PlayerCharacterDetailResponse>> damagePlayerCharacter(
            @PathVariable UUID id, @RequestParam int amount) {
        PlayerCharacterDetailResponse response = (PlayerCharacterDetailResponse) service.takeDamage(id, amount);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Heal player character")
    @PostMapping("/{id}/heal")
    public ResponseEntity<EntityModel<PlayerCharacterDetailResponse>> healPlayerCharacter(
            @PathVariable UUID id, @RequestParam int amount) {
        PlayerCharacterDetailResponse response = (PlayerCharacterDetailResponse) service.heal(id, amount);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    private EntityModel<PlayerCharacterDetailResponse> buildEntityModel(PlayerCharacterDetailResponse dto) {
        Link selfLink = linkTo(methodOn(PlayerCharacterController.class).getPlayerCharacter(dto.id())).withSelfRel();
        Link updateLink = linkTo(methodOn(PlayerCharacterController.class).updatePlayerCharacter(dto.id(), null)).withRel("update");
        Link deleteLink = linkTo(methodOn(PlayerCharacterController.class).deletePlayerCharacter(dto.id())).withRel("delete");
        Link listLink = linkTo(methodOn(CharacterController.class).getAllCharacters()).withRel("list");
        Link damageLink = linkTo(methodOn(PlayerCharacterController.class).damagePlayerCharacter(dto.id(), 0)).withRel("damage");
        Link healLink = linkTo(methodOn(PlayerCharacterController.class).healPlayerCharacter(dto.id(), 0)).withRel("heal");
        return EntityModel.of(dto, selfLink, updateLink, deleteLink, listLink, damageLink, healLink);
    }
}

