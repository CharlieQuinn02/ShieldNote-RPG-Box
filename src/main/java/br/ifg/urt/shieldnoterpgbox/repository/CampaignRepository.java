package br.ifg.urt.shieldnoterpgbox.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    
    default Campaign findByIdOrThrow(UUID id) {
        return findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
    }
}