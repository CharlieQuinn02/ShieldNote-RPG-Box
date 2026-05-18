package br.ifg.urt.shieldnoterpgbox.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.dto.request.EncounterRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.EncounterResponseDTO;
import br.ifg.urt.shieldnoterpgbox.service.EncounterCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/encounters")
@Tag(name = "Calculadora de Encontros", description = "Ferramentas e utilitários matemáticos para auxiliar os mestres durante a sessão")
public class EncounterController {

    private final EncounterCalculator calculatorService;

    public EncounterController(EncounterCalculator calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Operation(summary = "Calcular dificuldade do combate", description = "Processa os níveis dos jogadores e o XP dos monstros para determinar se o encontro será Fácil, Médio, Difícil ou Mortal, retornando também os limites de XP ajustados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cálculo matemático realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados (ex: listas de jogadores ou monstros vazias)")
    })
    @PostMapping("/calcular")
    public ResponseEntity<EncounterResponseDTO> calcularEncontro(@Valid @RequestBody EncounterRequestDTO dto) {
        
        // chama o método EncounterCalculator passando o DTO que chegou
        EncounterResponseDTO resultado = calculatorService.calcularDificuldade(dto);
        
        return ResponseEntity.ok(resultado);
    }
}