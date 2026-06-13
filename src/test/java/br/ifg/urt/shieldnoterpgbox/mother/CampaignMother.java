package br.ifg.urt.shieldnoterpgbox.mother;

import java.time.LocalDateTime;
import java.util.UUID;

import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;

public class CampaignMother {

    // Retorna uma campanha padrão, ativa e com todos os dados preenchidos
    public static Campaign campanhaAtiva() {
        Campaign campaign = new Campaign();
        campaign.setId(UUID.randomUUID());
        campaign.setMestreId(UUID.randomUUID());
        campaign.setTitulo("A Mina Perdida de Phandelver");
        campaign.setDescricao("Uma aventura clássica para heróis de nível 1 a 5.");
        campaign.setSistema("D&D 5e");
        campaign.setStatus(StatusEnum.ATIVA);
        campaign.setCriadaEm(LocalDateTime.now());
        return campaign;
    }

    // Retorna uma campanha que já está pausada, podendo testar a reativação
    public static Campaign campanhaPausada() {
        Campaign campaign = campanhaAtiva(); // Aproveita a estrutura da ativa
        campaign.setStatus(StatusEnum.PAUSADA); // Só muda o que importa
        return campaign;
    }
}