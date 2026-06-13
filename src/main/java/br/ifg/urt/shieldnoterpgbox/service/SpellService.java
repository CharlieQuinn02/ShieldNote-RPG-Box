package br.ifg.urt.shieldnoterpgbox.service;

import br.ifg.urt.shieldnoterpgbox.dto.request.SpellRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellComplexidadeResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResumoResponse;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.mapper.SpellMapper;
import br.ifg.urt.shieldnoterpgbox.model.Spell;
import br.ifg.urt.shieldnoterpgbox.repository.SpellRepository;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SpellService {

    private final SpellRepository spellRepository;
    private final SpellMapper spellMapper;

    // Construtor manual
    public SpellService(SpellRepository spellRepository, SpellMapper spellMapper) {
        this.spellRepository = spellRepository;
        this.spellMapper = spellMapper;
    }

    @CacheEvict(value = "spellsFiltrados", allEntries = true)
    @Transactional
    public SpellResponse criar(SpellRequest request) {
        return spellMapper.toResponseDTO(spellRepository.save(spellMapper.toEntity(request)));
    }

    @Cacheable(value = "spellsFiltrados", key = "'all'")
    @Transactional(readOnly = true)
    public List<SpellResumoResponse> listarTodos() {
        return spellMapper.toResumoDTOList(spellRepository.findAll());
    }

    @Cacheable(value = "spells", key = "#id")
    @Transactional(readOnly = true)
    public SpellResponse buscarPorId(UUID id) {
        return spellMapper.toResponseDTO(findById(id));
    }

    @Cacheable(value = "spellsFiltrados", key = "'school:' + #school")
    @Transactional(readOnly = true)
    public List<SpellResumoResponse> buscarPorEscola(String school) {
        return spellMapper.toResumoDTOList(spellRepository.findBySchool(school));
    }

    @Cacheable(value = "spellsFiltrados", key = "'level:' + #level")
    @Transactional(readOnly = true)
    public List<SpellResumoResponse> buscarPorNivel(int level) {
        return spellMapper.toResumoDTOList(spellRepository.findByLevel(level));
    }

    @Cacheable(value = "spellsFiltrados", key = "'nome:' + #nome")
    @Transactional(readOnly = true)
    public List<SpellResumoResponse> buscarPorNome(String nome) {
        return spellMapper.toResumoDTOList(spellRepository.findByNomeContainingIgnoreCase(nome));
    }

    @Cacheable(value = "spellsComplexidade", key = "#id")
    @Transactional(readOnly = true)
    public SpellComplexidadeResponse getRelatorioComplexidade(UUID id) {
        return spellMapper.toComplexidadeDTO(findById(id));
    }

    @Caching(evict = {
            @CacheEvict(value = "spells", key = "#id"),
            @CacheEvict(value = "spellsComplexidade", key = "#id"),
            @CacheEvict(value = "spellsFiltrados", allEntries = true)
    })
    @Transactional
    public SpellResponse atualizar(UUID id, SpellRequest request) {
        findById(id);
        Spell s = spellMapper.toEntity(request);
        s.setId(id);
        return spellMapper.toResponseDTO(spellRepository.save(s));
    }

    @Caching(evict = {
            @CacheEvict(value = "spells", key = "#id"),
            @CacheEvict(value = "spellsComplexidade", key = "#id"),
            @CacheEvict(value = "spellsFiltrados", allEntries = true)
    })
    @Transactional
    public void deletar(UUID id) { spellRepository.delete(findById(id)); }

    private Spell findById(UUID id) {
        
        return spellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Spell não encontrado com id: " + id));
    }
}