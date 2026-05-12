package br.ifg.urt.shieldnoterpgbox.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.shieldnoterpgbox.model.Notes;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;

@Repository
public interface NotesRepository extends JpaRepository<Notes, UUID> {

    default Notes findByIdOrThrow(UUID id) {
        return findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada com o ID: " + id));
    }
}