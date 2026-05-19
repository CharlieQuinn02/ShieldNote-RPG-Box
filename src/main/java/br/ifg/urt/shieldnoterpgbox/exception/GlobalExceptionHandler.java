package br.ifg.urt.shieldnoterpgbox.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // tratamento para os erros do RequestDTO 
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> listaDeErros = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .toList();

        String detalhesErro = String.join("\n", listaDeErros);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                "Erro de validação: " + listaDeErros.size() + " campo(s) inválido(s)",
                detalhesErro);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // tratamento para erros de validação em @RequestParam e @PathVariable
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        
        String mensagemLimpa = ex.getMessage();
        
        if (mensagemLimpa.contains(": ")) {
            mensagemLimpa = mensagemLimpa.split(": ")[1];
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                "Erro de validação de parâmetro",
                mensagemLimpa);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // tratamento para recursos não encontrados (Ex: Campanha ou Nota inexistente)
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    
 // tratamento para as regras de negócio disparadas pelos Value Objects (VOs)
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                "Regra de negócio violada",
                ex.getMessage()); // Vai devolver a  mensagem do VO ("O máximo de jogadores não pode...")

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    
}