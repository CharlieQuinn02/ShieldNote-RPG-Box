package br.ifg.urt.shieldnoterpgbox.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    // método default
    default Campaign findByIdOrThrow(UUID id) {
        return findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
    }
    
    // busca com paginação
    Page<Campaign> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}