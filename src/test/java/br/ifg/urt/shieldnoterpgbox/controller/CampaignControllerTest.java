package br.ifg.urt.shieldnoterpgbox.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; 
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ifg.urt.shieldnoterpgbox.assembler.CampaignModelAssembler;
import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.service.CampaignService;

@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // Corrigido para @MockBean
    private CampaignService service;

    @MockBean // Corrigido para @MockBean
    private CampaignModelAssembler assembler;

    @Test
    @DisplayName("Deve criar campanha com sucesso e retornar HTTP 201 Created")
    void deveCriarCampanhaComSucesso() throws Exception {
        // Arrange
        UUID mestreId = UUID.randomUUID();
        UUID campanhaId = UUID.randomUUID();
        
        // Instanciando o Record com todos os parâmetros na ordem correta
        CampaignRequestDTO requestDTO = new CampaignRequestDTO(
                mestreId, 
                "A Maldição de Strahd", 
                "Uma aventura sombria", 
                "D&D 5e", 
                3, 
                5
        );

        // instanciando o Record de resposta
        CampaignResponseDTO responseDTO = new CampaignResponseDTO(
                campanhaId, 
                mestreId, 
                "A Maldição de Strahd", 
                "Uma aventura sombria", 
                "D&D 5e", 
                StatusEnum.ATIVA, 
                3, 
                5, 
                "Capacidade para 3 a 5 jogadores", 
                LocalDateTime.now()
        );

        when(service.create(any(CampaignRequestDTO.class))).thenReturn(responseDTO);
        when(assembler.toModel(responseDTO)).thenReturn(EntityModel.of(responseDTO));

        // Act & Assert
        mockMvc.perform(post("/campaigns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("A Maldição de Strahd"))
                .andExpect(jsonPath("$.status").value("ATIVA"));
                
        verify(service).create(any(CampaignRequestDTO.class));
    }

    @Test
    @DisplayName("Deve retornar HTTP 404 Not Found quando buscar campanha inexistente")
    void deveRetornar404QuandoCampanhaNaoEncontrada() throws Exception {
        // Arrange
        UUID idInexistente = UUID.randomUUID();
        when(service.findById(idInexistente)).thenThrow(new ResourceNotFoundException("Campanha não encontrada"));

        // Act & Assert
        mockMvc.perform(get("/campaigns/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar HTTP 204 No Content ao deletar uma campanha")
    void deveRetornar204AoDeletarCampanha() throws Exception {
        // Arrange
        UUID idCampanha = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(delete("/campaigns/{id}", idCampanha))
                .andExpect(status().isNoContent());

        verify(service).delete(idCampanha);
    }

    @Test
    @DisplayName("Deve retornar HTTP 400 Bad Request quando os dados enviados forem inválidos")
    void deveRetornar400QuandoDadosInvalidos() throws Exception {
        // Arrange
        // Passando "null" para os campos obrigatórios para forçar o erro no @Valid
        CampaignRequestDTO requestInvalido = new CampaignRequestDTO(
                null, null, null, null, null, null
        ); 

        // Act & Assert
        mockMvc.perform(post("/campaigns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                
                .andExpect(status().isBadRequest()); // O controller bloqueia aqui e devolve 400

        // mockito garante que o Service foi guardado pela validação e nunca foi chamado
        verify(service, never()).create(any());
    }
}