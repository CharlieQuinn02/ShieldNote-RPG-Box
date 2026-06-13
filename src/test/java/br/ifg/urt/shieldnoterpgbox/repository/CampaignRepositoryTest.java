package br.ifg.urt.shieldnoterpgbox.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import br.ifg.urt.shieldnoterpgbox.model.vo.CapacidadeJogadores;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CampaignRepositoryTest {

    @Autowired
    private CampaignRepository repository; //  Spring injeta o repositório real aqui

 // Helper: Cria uma campanha limpa
    private Campaign criarCampanhaParaSalvar() {
        Campaign campaign = new Campaign();
        campaign.setMestreId(UUID.randomUUID());
        campaign.setTitulo("A Maldição de Strahd");
        campaign.setDescricao("Uma aventura sombria.");
        campaign.setSistema("D&D 5e");
        campaign.setStatus(StatusEnum.ATIVA);
        campaign.setCriadaEm(LocalDateTime.now());
        
        //ordem correta de máximo e mínimo
        campaign.setCapacidade(new CapacidadeJogadores(5, 2));
        
        return campaign;
    }

    @Test
    @DisplayName("Deve salvar uma campanha com sucesso no banco de dados")
    void deveSalvarCampanha() {
        // Arrange
        Campaign novaCampanha = criarCampanhaParaSalvar();

        // Act
        Campaign campanhaSalva = repository.save(novaCampanha);

        // Assert
        assertNotNull(campanhaSalva.getId()); // Comprova que o banco gerou o UUID corretamente
        assertEquals("A Maldição de Strahd", campanhaSalva.getTitulo());
    }

    @Test
    @DisplayName("Deve dar erro ao tentar salvar campanha sem título (Testando Constraint NOT NULL)")
    void deveLancarErroAoSalvarCampanhaSemTitulo() {
        // Arrange
        Campaign campanhaInvalida = criarCampanhaParaSalvar();
        campanhaInvalida.setTitulo(null); // Violando a regra @Column(nullable = false) no model

        // Act & Assert
        // usa-se saveAndFlush() para obrigar o Spring a tentar gravar no banco na mesma hora
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(campanhaInvalida);
        });
    }

    @Test
    @DisplayName("Deve encontrar campanhas paginadas e ignorar maiúsculas/minúsculas (IgnoreCase)")
    void deveEncontrarCampanhasPorTitulo() {
        // Arrange
        String tituloUnico = "Aventura Super Exclusiva";
        
        Campaign c1 = criarCampanhaParaSalvar();
        c1.setTitulo(tituloUnico); // Título que não existe
        repository.save(c1);

        Campaign c2 = criarCampanhaParaSalvar();
        c2.setTitulo("A Mina Perdida de Phandelver");
        repository.save(c2);

        Pageable paginacao = PageRequest.of(0, 10);

        // Act
        // pesquisando por um termo que só a c1 possui
        Page<Campaign> resultado = repository.findByTituloContainingIgnoreCase("SUPER EXCLUSIVA", paginacao);

        // Assert
        assertEquals(1, resultado.getTotalElements()); 
        assertEquals(tituloUnico, resultado.getContent().get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve encontrar campanha por ID usando o método default findByIdOrThrow")
    void deveEncontrarCampanhaPorId() {
        // Arrange
        Campaign salva = repository.save(criarCampanhaParaSalvar());

        // Act
        Campaign encontrada = repository.findByIdOrThrow(salva.getId());

        // Assert
        assertNotNull(encontrada);
        assertEquals(salva.getId(), encontrada.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ResourceNotFoundException ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            repository.findByIdOrThrow(idInexistente);
        });
    }
}