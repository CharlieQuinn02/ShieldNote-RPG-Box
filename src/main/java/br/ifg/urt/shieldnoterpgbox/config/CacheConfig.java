package br.ifg.urt.shieldnoterpgbox.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager(
                // Spells
                "spells",
                "spellsFiltrados",
                "spellsComplexidade",
                
                // SpellCastings
                "spellCastings",
                "spellCastingsFiltrados",
                
                // SpellSlots
                "spellSlots",
                
                // Characters 
                "character-summary",
                "character-detail", 
                
                // Campaigns
                "campaigns"
        );
        
        manager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .recordStats()); // habilita métricas (útil para debug)
                
        return manager;
    }
}