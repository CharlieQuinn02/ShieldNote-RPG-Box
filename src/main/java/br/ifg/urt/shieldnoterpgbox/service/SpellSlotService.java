package br.ifg.urt.shieldnoterpgbox.service;

import br.ifg.urt.shieldnoterpgbox.dto.request.SpellSlotRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellSlotResponse;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.mapper.SpellSlotMapper;
import br.ifg.urt.shieldnoterpgbox.model.SpellCasting;
import br.ifg.urt.shieldnoterpgbox.model.SpellSlot;
import br.ifg.urt.shieldnoterpgbox.repository.SpellCastingRepository;
import br.ifg.urt.shieldnoterpgbox.repository.SpellSlotRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class SpellSlotService {

    private final SpellSlotRepository spellSlotRepository;
    private final SpellCastingRepository spellCastingRepository;
    private final SpellSlotMapper spellSlotMapper;

    
    public SpellSlotService(SpellSlotRepository spellSlotRepository, 
                            SpellCastingRepository spellCastingRepository, 
                            SpellSlotMapper spellSlotMapper) {
        this.spellSlotRepository = spellSlotRepository;
        this.spellCastingRepository = spellCastingRepository;
        this.spellSlotMapper = spellSlotMapper;
    }

    @CacheEvict(value = "spellSlots", allEntries = true)
    @Transactional
    public SpellSlotResponse criar(SpellSlotRequest request) {
        SpellCasting sc = spellCastingRepository.findById(request.spellCastingId())
                .orElseThrow(() -> new ResourceNotFoundException("SpellCasting não encontrado com id: " + request.spellCastingId()));
        
        // usando construtor nativo
        SpellSlot slot = new SpellSlot(
                request.nivel(), 
                request.quantidadeAtual(), 
                request.quantidadeMaxima(), 
                sc
        );
        
        return spellSlotMapper.toResponseDTO(spellSlotRepository.save(slot));
    }

    @Cacheable(value = "spellSlots", key = "#id")
    @Transactional(readOnly = true)
    public SpellSlotResponse buscarPorId(UUID id) {
        return spellSlotMapper.toResponseDTO(findById(id));
    }

    @Cacheable(value = "spellSlots", key = "'casting:' + #spellCastingId")
    @Transactional(readOnly = true)
    public List<SpellSlotResponse> buscarPorSpellCasting(UUID spellCastingId) {
        return spellSlotMapper.toResponseDTOList(spellSlotRepository.findBySpellCastingId(spellCastingId));
    }

    @CacheEvict(value = "spellSlots", allEntries = true)
    @Transactional
    public SpellSlotResponse atualizar(UUID id, SpellSlotRequest request) {
        SpellSlot slot = findById(id);
        slot.setNivel(request.nivel());
        slot.setQuantidadeAtual(request.quantidadeAtual());
        slot.setQuantidadeMaxima(request.quantidadeMaxima());
        return spellSlotMapper.toResponseDTO(spellSlotRepository.save(slot));
    }

    @CacheEvict(value = "spellSlots", allEntries = true)
    @Transactional
    public void deletar(UUID id) { spellSlotRepository.delete(findById(id)); }

    public SpellSlotResponse toResponse(SpellSlot slot) { return spellSlotMapper.toResponseDTO(slot); }

    private SpellSlot findById(UUID id) {
        return spellSlotRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SpellSlot não encontrado com id: " + id));
    }
}