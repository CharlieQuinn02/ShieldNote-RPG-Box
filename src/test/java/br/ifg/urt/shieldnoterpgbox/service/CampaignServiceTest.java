package br.ifg.urt.shieldnoterpgbox.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import br.ifg.urt.shieldnoterpgbox.mother.CampaignMother;
import br.ifg.urt.shieldnoterpgbox.repository.CampaignRepository;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceTest {

    @InjectMocks
    private CampaignService service;

    @Mock
    private CampaignRepository repository;

    @Test
    @DisplayName("Deve encerrar a campanha com sucesso e salvar no repositório")
    void deveEncerrarCampanhaComSucesso() {
        // Arrange
        UUID idCampanha = UUID.randomUUID();
        Campaign campanhaAtiva = CampaignMother.campanhaAtiva(); 
        
        //  Ensinando o mock a responder ao findByIdOrThrow 
        when(repository.findByIdOrThrow(idCampanha)).thenReturn(campanhaAtiva);
        
        when(repository.save(any(Campaign.class))).thenReturn(campanhaAtiva);

        // Act
        service.encerrar(idCampanha);

        // Assert
        assertEquals(StatusEnum.FINALIZADA, campanhaAtiva.getStatus());
        verify(repository).save(campanhaAtiva);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar encerrar uma campanha que não existe (Fail Fast)")
    void deveLancarExcecaoQuandoCampanhaNaoExiste() {
        // Arrange
        UUID idInvalido = UUID.randomUUID();
        
        // Ensinando o mock a lançar a exceção direto no findByIdOrThrow
        when(repository.findByIdOrThrow(idInvalido)).thenThrow(new ResourceNotFoundException("Campanha não encontrada"));

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.encerrar(idInvalido);
        });

        // Garante que não tentou salvar no banco
        verify(repository, never()).save(any());
    }
}