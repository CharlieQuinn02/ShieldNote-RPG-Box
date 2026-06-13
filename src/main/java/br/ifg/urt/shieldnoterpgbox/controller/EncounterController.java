package br.ifg.urt.shieldnoterpgbox.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; 
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
@Validated 
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
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados (ex: valores nulos, negativos ou listas vazias)")
    })
    @PostMapping("/calcular")
    public ResponseEntity<EncounterResponseDTO> calcularEncontro(@Valid @RequestBody EncounterRequestDTO dto) { //Valid aciona as regras do record
        
        // chama o método EncounterCalculator passando o DTO que chegou com dados limpos
        EncounterResponseDTO resultado = calculatorService.calcularDificuldade(dto);
        
        return ResponseEntity.ok(resultado);
    }
}