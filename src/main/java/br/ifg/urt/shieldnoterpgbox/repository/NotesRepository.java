package br.ifg.urt.shieldnoterpgbox.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.model.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, UUID> {

    // Método padrão para buscar ou lançar a exceção tratada
    default Notes findByIdOrThrow(UUID id) {
        return findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada com o ID: " + id));
    }

    
    // METODOS PARA CONSULTA PAGINADA E FILTROS 
    

    // Filtro 1: Busca paginada apenas por título 
    Page<Notes> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    
    // Filtro 2: Busca paginada apenas por categoria 
    Page<Notes> findByCategoria(PostItCat categoria, Pageable pageable);
    
    // Filtro 3: Busca paginada combinando título e categoria ao mesmo tempo
    Page<Notes> findByTituloContainingIgnoreCaseAndCategoria(String titulo, PostItCat categoria, Pageable pageable);
}