package br.ifg.urt.shieldnoterpgbox.repository;

import br.ifg.urt.shieldnoterpgbox.model.SpellCasting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository
public interface SpellCastingRepository extends JpaRepository<SpellCasting, UUID> {
    List<SpellCasting> findByCharacterId(UUID characterId);
}


