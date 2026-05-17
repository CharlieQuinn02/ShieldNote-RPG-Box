package br.ifg.urt.shieldnoterpgbox.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.dto.request.EncounterRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.EncounterResponseDTO;
import br.ifg.urt.shieldnoterpgbox.service.EncounterCalculator;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/encounters")
public class EncounterController {

    private final EncounterCalculator calculatorService;

    public EncounterController(EncounterCalculator calculatorService) {
        this.calculatorService = calculatorService;
    }

    // Endpoint para realizar o cálculo
    @PostMapping("/calcular")
    public ResponseEntity<EncounterResponseDTO> calcularEncontro(@Valid @RequestBody EncounterRequestDTO dto) {
        
        // chama o método EncounterCalculator passando o DTO que chegou
        EncounterResponseDTO resultado = calculatorService.calcularDificuldade(dto);
        
        return ResponseEntity.ok(resultado);
    }
}