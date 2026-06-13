package br.ifg.urt.shieldnoterpgbox.repository;

import br.ifg.urt.shieldnoterpgbox.model.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository
public interface SpellRepository extends JpaRepository<Spell, UUID> {
    List<Spell> findBySchool(String school);
    List<Spell> findByLevel(int level);
    List<Spell> findByNomeContainingIgnoreCase(String nome);
}


