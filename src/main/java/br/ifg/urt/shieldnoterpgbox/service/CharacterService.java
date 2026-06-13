package br.ifg.urt.shieldnoterpgbox.service;

import br.ifg.urt.shieldnoterpgbox.dto.request.MonsterRequest;
import br.ifg.urt.shieldnoterpgbox.dto.request.NPCRequest;
import br.ifg.urt.shieldnoterpgbox.dto.request.PlayerCharacterRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.CharacterSummaryResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.MonsterDetailResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.NPCDetailResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.PlayerCharacterDetailResponse;
import br.ifg.urt.shieldnoterpgbox.exception.InvalidGameRuleException;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.mapper.CharacterMapper;
import br.ifg.urt.shieldnoterpgbox.model.Character;
import br.ifg.urt.shieldnoterpgbox.model.Monster;
import br.ifg.urt.shieldnoterpgbox.model.NPC;
import br.ifg.urt.shieldnoterpgbox.model.PlayerCharacter;
import br.ifg.urt.shieldnoterpgbox.model.vo.Skill;
import br.ifg.urt.shieldnoterpgbox.repository.CharacterRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {

	private final CharacterRepository repository;
	private final CharacterMapper mapper;
	private final CharacterCalculator calculator;

	public CharacterService(CharacterRepository repository, CharacterMapper mapper, CharacterCalculator calculator) {
		this.repository = repository;
		this.mapper = mapper;
		this.calculator = calculator;
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "character-summary")
	public List<CharacterSummaryResponse> getAllCharacters() {
		return repository.findAll().stream()
				.map(mapper::toSummary)
				.toList();
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "character-detail", key = "#id")
	public Object getCharacterById(UUID id) {
		return buildDetailResponse(repository.findByIdOrThrow(id));
	}

	
	public List<Character> getCharacterByName(String name) {
	    
	    List<Character> personagens = repository.findByNameContainingIgnoreCase(name);
	    
	    if (personagens.isEmpty()) {
	        throw new ResourceNotFoundException("Nenhum personagem encontrado com o termo: " + name);
	    }
	    
	    return personagens;
	}
	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public MonsterDetailResponse createMonster(MonsterRequest request) {
		validateNpcSkillLimit(request.skills());
		Monster monster = mapper.toMonsterEntity(request);
		monster.setCurrentHitPoints(request.currentHitPoints() != null ? request.currentHitPoints() : monster.getHitPoints());
		monster.setProficiencyBonus(request.proficiencyBonus() != null ? request.proficiencyBonus() : 2);
		if (request.skills() != null) monster.getProficiencies().setSkills(request.skills());
		if (request.savingThrows() != null) monster.getProficiencies().setSavingThrows(request.savingThrows());
		return (MonsterDetailResponse) buildDetailResponse(repository.save(monster));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public NPCDetailResponse createNpc(NPCRequest request) {
		validateNpcSkillLimit(request.skills());
		NPC npc = mapper.toNpcEntity(request);
		npc.setCurrentHitPoints(request.currentHitPoints() != null ? request.currentHitPoints() : npc.getHitPoints());
		npc.setProficiencyBonus(request.proficiencyBonus() != null ? request.proficiencyBonus() : 2);
		if (request.skills() != null) npc.getProficiencies().setSkills(request.skills());
		if (request.savingThrows() != null) npc.getProficiencies().setSavingThrows(request.savingThrows());
		return (NPCDetailResponse) buildDetailResponse(repository.save(npc));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public PlayerCharacterDetailResponse createPlayerCharacter(PlayerCharacterRequest request) {
		PlayerCharacter pc = mapper.toPlayerCharacterEntity(request);
		pc.setCurrentHitPoints(request.currentHitPoints() != null ? request.currentHitPoints() : pc.getHitPoints());
		pc.setProficiencyBonus(request.proficiencyBonus() != null ? request.proficiencyBonus() : 2);
		if (request.skills() != null) pc.getProficiencies().setSkills(request.skills());
		if (request.savingThrows() != null) pc.getProficiencies().setSavingThrows(request.savingThrows());
		return (PlayerCharacterDetailResponse) buildDetailResponse(repository.save(pc));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public MonsterDetailResponse updateMonster(UUID id, MonsterRequest request) {
		validateNpcSkillLimit(request.skills());
		Monster monster = (Monster) repository.findByIdOrThrow(id);
		monster.setName(request.name());
		monster.setHitPoints(request.hitPoints());
		monster.setCurrentHitPoints(request.currentHitPoints() != null ? request.currentHitPoints() : request.hitPoints());
		monster.setArmorClass(request.armorClass());
		monster.setSpeed(request.speed());
		monster.setAlignment(request.alignment());
		monster.setProficiencyBonus(request.proficiencyBonus() != null ? request.proficiencyBonus() : 2);
		monster.setAttributes(mapper.toAtributo(request.attributes()));
		if (request.skills() != null) monster.getProficiencies().setSkills(request.skills());
		if (request.savingThrows() != null) monster.getProficiencies().setSavingThrows(request.savingThrows());
		if (request.size() != null) monster.setSize(request.size());
		monster.setMonsterType(request.monsterType());
		monster.setSubtype(request.subtype());
		if (request.challengeRating() != null) monster.setChallengeRating(request.challengeRating());
		if (request.xpBase() != null) monster.setXpBase(request.xpBase());
		if (request.actions() != null) monster.setActions(request.actions());
		return (MonsterDetailResponse) buildDetailResponse(repository.save(monster));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public NPCDetailResponse updateNpc(UUID id, NPCRequest request) {
		validateNpcSkillLimit(request.skills());
		NPC npc = (NPC) repository.findByIdOrThrow(id);
		npc.setName(request.name());
		npc.setHitPoints(request.hitPoints());
		npc.setCurrentHitPoints(request.currentHitPoints() != null ? request.currentHitPoints() : request.hitPoints());
		npc.setArmorClass(request.armorClass());
		npc.setSpeed(request.speed());
		npc.setAlignment(request.alignment());
		npc.setProficiencyBonus(request.proficiencyBonus() != null ? request.proficiencyBonus() : 2);
		npc.setAttributes(mapper.toAtributo(request.attributes()));
		if (request.skills() != null) npc.getProficiencies().setSkills(request.skills());
		if (request.savingThrows() != null) npc.getProficiencies().setSavingThrows(request.savingThrows());
		npc.setOccupation(request.occupation());
		npc.setDescription(request.description());
		npc.setIsHostile(request.isHostile());
		npc.setReward(request.reward());
		return (NPCDetailResponse) buildDetailResponse(repository.save(npc));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public PlayerCharacterDetailResponse updatePlayerCharacter(UUID id, PlayerCharacterRequest request) {
		PlayerCharacter pc = (PlayerCharacter) repository.findByIdOrThrow(id);
		pc.setName(request.name());
		pc.setHitPoints(request.hitPoints());
		pc.setCurrentHitPoints(request.currentHitPoints() != null ? request.currentHitPoints() : request.hitPoints());
		pc.setArmorClass(request.armorClass());
		pc.setSpeed(request.speed());
		pc.setAlignment(request.alignment());
		pc.setProficiencyBonus(request.proficiencyBonus() != null ? request.proficiencyBonus() : 2);
		pc.setAttributes(mapper.toAtributo(request.attributes()));
		if (request.skills() != null) pc.getProficiencies().setSkills(request.skills());
		if (request.savingThrows() != null) pc.getProficiencies().setSavingThrows(request.savingThrows());
		pc.setRace(request.race());
		pc.setCharClass(request.charClass());
		pc.setSubclass(request.subclass());
		if (request.level() != null) pc.setLevel(request.level());
		if (request.money() != null) pc.setMoney(request.money());
		return (PlayerCharacterDetailResponse) buildDetailResponse(repository.save(pc));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public void deleteCharacter(UUID id) {
		repository.delete(repository.findByIdOrThrow(id));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public Object takeDamage(UUID id, int amount) {
		Character character = repository.findByIdOrThrow(id);
		character.adjustHitPoints(-amount);
		return buildDetailResponse(repository.save(character));
	}

	@Transactional
	@CacheEvict(value = {"character-summary", "character-detail"}, allEntries = true)
	public Object heal(UUID id, int amount) {
		Character character = repository.findByIdOrThrow(id);
		character.adjustHitPoints(amount);
		return buildDetailResponse(repository.save(character));
	}

	private Object buildDetailResponse(Character character) {
		var skills = calculator.calculateSkills(character);
		var savingThrows = calculator.calculateSavingThrows(character);

		return switch (character) {
		case Monster m -> {
			MonsterDetailResponse dto = mapper.toMonsterDetail(m);
			yield new MonsterDetailResponse(
					dto.id(), dto.name(), dto.hitPoints(), dto.currentHitPoints(),
					dto.armorClass(), dto.speed(), dto.alignment(), dto.proficiencyBonus(),
					dto.attributes(), skills, savingThrows,
					dto.size(), dto.monsterType(), dto.subtype(),
					dto.challengeRating(), dto.xpBase(), dto.actions()
					);
		}
		case NPC n -> {
			NPCDetailResponse dto = mapper.toNpcDetail(n);
			yield new NPCDetailResponse(
					dto.id(), dto.name(), dto.hitPoints(), dto.currentHitPoints(),
					dto.armorClass(), dto.speed(), dto.alignment(), dto.proficiencyBonus(),
					dto.attributes(), skills, savingThrows,
					dto.occupation(), dto.description(), dto.isHostile(), dto.reward()
					);
		}
		case PlayerCharacter p -> {
			PlayerCharacterDetailResponse dto = mapper.toPlayerCharacterDetail(p);
			yield new PlayerCharacterDetailResponse(
					dto.id(), dto.name(), dto.hitPoints(), dto.currentHitPoints(),
					dto.armorClass(), dto.speed(), dto.alignment(), dto.proficiencyBonus(),
					dto.attributes(), skills, savingThrows,
					dto.race(), dto.charClass(), dto.subclass(),
					dto.level(), dto.money()
					);
		}
		default -> throw new IllegalArgumentException("Unknown character type");
		};
	}

	private void validateNpcSkillLimit(Set<Skill> skills) {
		if (skills != null && skills.size() > 1) {
			throw new InvalidGameRuleException("Monster/NPC can have at most 1 skill proficiency");
		}
	}
}



