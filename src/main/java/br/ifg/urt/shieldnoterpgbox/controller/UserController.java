package br.ifg.urt.shieldnoterpgbox.controller;

import br.ifg.urt.shieldnoterpgbox.assembler.UserModelAssembler;
import br.ifg.urt.shieldnoterpgbox.dto.request.UserRequest;
import br.ifg.urt.shieldnoterpgbox.exception.ExceptionResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.UserResponse;
import br.ifg.urt.shieldnoterpgbox.exception.BusinessException;
import br.ifg.urt.shieldnoterpgbox.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Validated
@Tag(name = "Usuários", description = "Endpoints de registro, autenticação e administração de usuários")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    // Construtor manual 
    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @PostMapping(value = "/registrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registrar usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "Email já cadastrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<UserResponse>> registrar(@Valid @RequestBody UserRequest request) {
        if (request.senha() == null || request.senha().isBlank()) {
            throw new BusinessException("Senha é obrigatória no registro");
        }
        UserResponse response = userService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModelAssembler.toModel(response));
    }

    

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar usuários", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<List<EntityModel<UserResponse>>> listarTodos() {
        List<EntityModel<UserResponse>> models = userService.listarTodos().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(models);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar usuário por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<UserResponse>> buscarPorId(@PathVariable UUID id) {
        UserResponse response = userService.buscarPorId(id);
        return ResponseEntity.ok(userModelAssembler.toModel(response));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar usuário", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<UserResponse>> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequest request) {
        UserResponse response = userService.atualizar(id, request);
        return ResponseEntity.ok(userModelAssembler.toModel(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}