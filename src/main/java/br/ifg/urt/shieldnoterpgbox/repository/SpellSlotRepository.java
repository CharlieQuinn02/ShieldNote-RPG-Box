package br.ifg.urt.shieldnoterpgbox.repository;

import br.ifg.urt.shieldnoterpgbox.model.SpellSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository
public interface SpellSlotRepository extends JpaRepository<SpellSlot, UUID> {
    List<SpellSlot> findBySpellCastingId(UUID spellCastingId);
    List<SpellSlot> findBySpellCastingIdAndNivel(UUID spellCastingId, int nivel);
}



