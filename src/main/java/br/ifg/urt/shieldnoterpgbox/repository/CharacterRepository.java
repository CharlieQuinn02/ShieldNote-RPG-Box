package br.ifg.urt.shieldnoterpgbox.repository;

import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.model.Character;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, UUID> {
    
    // suporta busca parcial
    List<Character> findByNameContainingIgnoreCase(String name);

    default Character findByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + id));
    }
}
