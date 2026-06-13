package br.ifg.urt.shieldnoterpgbox.controller;
import br.ifg.urt.shieldnoterpgbox.dto.request.MonsterRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.MonsterDetailResponse;
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
@RequestMapping("/api/v1/monsters")
@Tag(name = "Monster")
@Validated
public class MonsterController {

    private final CharacterService service;

    public MonsterController(CharacterService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new monster")
    @PostMapping
    public ResponseEntity<EntityModel<MonsterDetailResponse>> createMonster(@Valid @RequestBody MonsterRequest request) {
        MonsterDetailResponse response = service.createMonster(request);
        URI location = URI.create("/api/v1/monsters/" + response.id());
        return ResponseEntity.created(location).body(buildEntityModel(response));
    }

    @Operation(summary = "Get monster by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<MonsterDetailResponse>> getMonster(@PathVariable UUID id) {
        MonsterDetailResponse response = (MonsterDetailResponse) service.getCharacterById(id);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Update monster by ID")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<MonsterDetailResponse>> updateMonster(
            @PathVariable UUID id, @Valid @RequestBody MonsterRequest request) {
        MonsterDetailResponse response = service.updateMonster(id, request);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Delete monster by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable UUID id) {
        service.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Apply damage to monster")
    @PostMapping("/{id}/damage")
    public ResponseEntity<EntityModel<MonsterDetailResponse>> damageMonster(
            @PathVariable UUID id, @RequestParam int amount) {
        MonsterDetailResponse response = (MonsterDetailResponse) service.takeDamage(id, amount);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    @Operation(summary = "Heal monster")
    @PostMapping("/{id}/heal")
    public ResponseEntity<EntityModel<MonsterDetailResponse>> healMonster(
            @PathVariable UUID id, @RequestParam int amount) {
        MonsterDetailResponse response = (MonsterDetailResponse) service.heal(id, amount);
        return ResponseEntity.ok(buildEntityModel(response));
    }

    private EntityModel<MonsterDetailResponse> buildEntityModel(MonsterDetailResponse dto) {
        Link selfLink = linkTo(methodOn(MonsterController.class).getMonster(dto.id())).withSelfRel();
        Link updateLink = linkTo(methodOn(MonsterController.class).updateMonster(dto.id(), null)).withRel("update");
        Link deleteLink = linkTo(methodOn(MonsterController.class).deleteMonster(dto.id())).withRel("delete");
        Link listLink = linkTo(methodOn(CharacterController.class).getAllCharacters()).withRel("list");
        Link damageLink = linkTo(methodOn(MonsterController.class).damageMonster(dto.id(), 0)).withRel("damage");
        Link healLink = linkTo(methodOn(MonsterController.class).healMonster(dto.id(), 0)).withRel("heal");
        return EntityModel.of(dto, selfLink, updateLink, deleteLink, listLink, damageLink, healLink);
    }
}

