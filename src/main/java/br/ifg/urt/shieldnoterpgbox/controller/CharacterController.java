package br.ifg.urt.shieldnoterpgbox.controller;

import br.ifg.urt.shieldnoterpgbox.dto.response.CharacterSummaryResponse;
import br.ifg.urt.shieldnoterpgbox.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/characters")
@Tag(name = "Character")
public class CharacterController {

	private final CharacterService service;

	public CharacterController(CharacterService service) {
		this.service = service;
	}

	@Operation(summary = "List all characters (summary)")
	@GetMapping
	public List<CharacterSummaryResponse> getAllCharacters() {
		return service.getAllCharacters();
	}

	@Operation(summary = "Get character by ID (detailed)")
	@GetMapping("/{id}")
	public EntityModel<?> getCharacterById(@PathVariable UUID id) {
		Object dto = service.getCharacterById(id);
		Link selfLink = linkTo(methodOn(CharacterController.class).getCharacterById(id)).withSelfRel();
		Link listLink = linkTo(methodOn(CharacterController.class).getAllCharacters()).withRel("list");
		Link deleteLink = linkTo(methodOn(CharacterController.class).deleteCharacter(id)).withRel("delete");
		return EntityModel.of(dto, selfLink, listLink, deleteLink);
	}

	@Operation(summary = "Get character by name")
	@GetMapping("/name/{name}")
	public CollectionModel<?> getCharacterByName(@PathVariable String name) {
		//  service devolve uma lista de personagens
		Iterable<?> dtos = (Iterable<?>) service.getCharacterByName(name);
		
		Link selfLink = linkTo(methodOn(CharacterController.class).getCharacterByName(name)).withSelfRel();
		Link listLink = linkTo(methodOn(CharacterController.class).getAllCharacters()).withRel("list");
		
		// collection model é bom para listas
		return CollectionModel.of(dtos, selfLink, listLink);
	}

	@Operation(summary = "Delete character by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCharacter(@PathVariable UUID id) {
		service.deleteCharacter(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Search characters by keyword")
	@GetMapping("/search")
	public List<CharacterSummaryResponse> searchCharacters(@RequestParam(required = false) String keyword) {
		if (keyword != null && !keyword.isBlank()) {
			return service.getAllCharacters().stream()
					.filter(c -> c.name().toLowerCase().contains(keyword.toLowerCase()))
					.toList();
		}
		return service.getAllCharacters();
	}
}