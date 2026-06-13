package br.ifg.urt.shieldnoterpgbox.service;

import br.ifg.urt.shieldnoterpgbox.dto.request.ConjurarRequest;
import br.ifg.urt.shieldnoterpgbox.dto.request.SpellCastingRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellCastingResponse;
import br.ifg.urt.shieldnoterpgbox.exception.BusinessException;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.mapper.SpellCastingMapper;
import br.ifg.urt.shieldnoterpgbox.model.SpellCasting;
import br.ifg.urt.shieldnoterpgbox.repository.SpellCastingRepository;
import br.ifg.urt.shieldnoterpgbox.repository.SpellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SpellCastingService {

    private final SpellCastingRepository spellCastingRepository;
    private final SpellRepository spellRepository;
    private final SpellCastingMapper spellCastingMapper;

    // construtor manual
    public SpellCastingService(SpellCastingRepository spellCastingRepository, 
                               SpellRepository spellRepository, 
                               SpellCastingMapper spellCastingMapper) {
        this.spellCastingRepository = spellCastingRepository;
        this.spellRepository = spellRepository;
        this.spellCastingMapper = spellCastingMapper;
    }
    @CacheEvict(value = {"spellCastings","spellCastingsFiltrados"}, allEntries = true)
    @Transactional
    public SpellCastingResponse criar(SpellCastingRequest request) {
        SpellCasting sc = spellCastingMapper.toEntity(request);
        return spellCastingMapper.toResponseDTO(spellCastingRepository.save(sc));
    }

    @Cacheable(value = "spellCastings", key = "#id")
    @Transactional(readOnly = true)
    public SpellCastingResponse buscarPorId(UUID id) {
        return spellCastingMapper.toResponseDTO(findById(id));
    }

    @Cacheable(value = "spellCastingsFiltrados", key = "'all'")
    @Transactional(readOnly = true)
    public List<SpellCastingResponse> listarTodos() {
        return spellCastingMapper.toResponseDTOList(spellCastingRepository.findAll());
    }

    @Cacheable(value = "spellCastingsFiltrados", key = "'character:' + #characterId")
    @Transactional(readOnly = true)
    public List<SpellCastingResponse> buscarPorCharacter(UUID characterId) {
        return spellCastingMapper.toResponseDTOList(spellCastingRepository.findByCharacterId(characterId));
    }

    @Caching(evict = {
            @CacheEvict(value = "spellCastings", key = "#id"),
            @CacheEvict(value = "spellCastingsFiltrados", allEntries = true)
    })
    @Transactional
    public SpellCastingResponse atualizar(UUID id, SpellCastingRequest request) {
        findById(id);
        SpellCasting sc = spellCastingMapper.toEntity(request);
        sc.setId(id);
        if (request.magiasConhecidas() != null)
            sc.setMagiasConhecidas(new ArrayList<>(request.magiasConhecidas()));
        return spellCastingMapper.toResponseDTO(spellCastingRepository.save(sc));
    }

    @Caching(evict = {
            @CacheEvict(value = "spellCastings", key = "#id"),
            @CacheEvict(value = "spellCastingsFiltrados", allEntries = true)
    })
    @Transactional
    public void deletar(UUID id) { spellCastingRepository.delete(findById(id)); }

    @Caching(evict = {
            @CacheEvict(value = "spellCastings", key = "#spellCastingId"),
            @CacheEvict(value = "spellCastingsFiltrados", allEntries = true)
    })
    @Transactional
    public boolean conjurar(UUID spellCastingId, ConjurarRequest request) {
        if (!spellRepository.existsById(request.spellId()))
            // CORREÇÃO 1: Concatenando a string
            throw new ResourceNotFoundException("Spell não encontrado com id: " + request.spellId());
        
        SpellCasting sc = findById(spellCastingId);
        if (!sc.conjurar(request.spellId(), request.nivelSlot()))
            throw new BusinessException("Conjuração falhou: magia não conhecida ou sem slots no nível " + request.nivelSlot());
        
        spellCastingRepository.save(sc);
        return true;
    }

    @Caching(evict = {
            @CacheEvict(value = "spellCastings", key = "#spellCastingId"),
            @CacheEvict(value = "spellCastingsFiltrados", allEntries = true)
    })
    @Transactional
    public SpellCastingResponse recuperarSlots(UUID spellCastingId) {
        SpellCasting sc = findById(spellCastingId);
        sc.recuperarSlots();
        return spellCastingMapper.toResponseDTO(spellCastingRepository.save(sc));
    }

    @Caching(evict = {
            @CacheEvict(value = "spellCastings", key = "#spellCastingId"),
            @CacheEvict(value = "spellCastingsFiltrados", allEntries = true)
    })
    @Transactional
    public SpellCastingResponse adicionarMagia(UUID spellCastingId, UUID spellId) {
        if (!spellRepository.existsById(spellId)) 
            
            throw new ResourceNotFoundException("Spell não encontrado com id: " + spellId);
        
        SpellCasting sc = findById(spellCastingId);
        if (!sc.getMagiasConhecidas().contains(spellId.toString()))
            sc.getMagiasConhecidas().add(spellId.toString());
        
        return spellCastingMapper.toResponseDTO(spellCastingRepository.save(sc));
    }

    @Caching(evict = {
            @CacheEvict(value = "spellCastings", key = "#spellCastingId"),
            @CacheEvict(value = "spellCastingsFiltrados", allEntries = true)
    })
    @Transactional
    public SpellCastingResponse removerMagia(UUID spellCastingId, UUID spellId) {
        SpellCasting sc = findById(spellCastingId);
        sc.getMagiasConhecidas().remove(spellId.toString());
        return spellCastingMapper.toResponseDTO(spellCastingRepository.save(sc));
    }

    private SpellCasting findById(UUID id) {
        
        return spellCastingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SpellCasting não encontrado com id: " + id));
    }
}