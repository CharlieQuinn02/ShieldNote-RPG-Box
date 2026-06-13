package br.ifg.urt.shieldnoterpgbox.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;

public class CampaignTest {

    private Campaign campaign;

    // Arrange base: executado antes de cada teste para garantir um objeto limpo
    @BeforeEach
    void setup() {
        campaign = new Campaign();
        campaign.setTitulo("A Mina Perdida de Phandelver");
        campaign.setStatus(StatusEnum.ATIVA);
    }

    @Test
    @DisplayName("Deve alterar o status para PAUSADA ao pausar a campanha")
    void devePausarCampanha() {
        // Arrange (já feito na configuração, campanha está ATIVA)

        // Act
        campaign.pausar();

        // Assert
        assertEquals(StatusEnum.PAUSADA, campaign.getStatus());
    }

    @Test
    @DisplayName("Deve alterar o status para ATIVA ao reativar a campanha")
    void deveReativarCampanha() {
        // Arrange
        // Forçamos o status para PAUSADA para testar a volta
        campaign.setStatus(StatusEnum.PAUSADA);

        // Act
        campaign.reativar();

        // Assert
        assertEquals(StatusEnum.ATIVA, campaign.getStatus());
    }

    @Test
    @DisplayName("Deve alterar o status para FINALIZADA ao encerrar a campanha")
    void deveEncerrarCampanha() {
        // Arrange (campanha está ATIVA)

        // Act
        campaign.encerrar();

        // Assert
        assertEquals(StatusEnum.FINALIZADA, campaign.getStatus());
    }

    @Test
    @DisplayName("Deve gerar e retornar um UUID válido ao iniciar uma sessão")
    void deveIniciarSessao() {
        // Arrange (campanha pronta)

        // Act
        UUID idSessao = campaign.iniciarSessao();

        // Assert
        assertNotNull(idSessao); // verifica se o UUID gerado não é nulo
    }
}
