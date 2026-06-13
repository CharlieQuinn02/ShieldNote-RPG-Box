package br.ifg.urt.shieldnoterpgbox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    // apenas mensagem completa
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // recebe nome e recurso
    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " não encontrado com ID: " + id);
    }
}