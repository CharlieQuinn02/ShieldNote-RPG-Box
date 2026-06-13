package br.ifg.urt.shieldnoterpgbox.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        String message = "JSON mal formatado ou campo obrigatório ausente";
        if (ex.getMessage() != null && ex.getMessage().contains("UUID")) {
            message = "Formato de UUID inválido. Formato esperado: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
        }
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), message, "Malformed JSON");
        
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        String message = String.format("Parâmetro obrigatório ausente: '%s'", ex.getParameterName());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), message, "Missing parameter");
        
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        String message = String.format("Método HTTP '%s' não suportado. Métodos suportados: %s",
                ex.getMethod(), ex.getSupportedHttpMethods());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), message, "Method not allowed");
        
        return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "A rota ou recurso solicitado não existe", "Resource not found");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        
        String mensagemLimpa = ex.getMessage();
        if (mensagemLimpa != null && mensagemLimpa.contains(": ")) {
            mensagemLimpa = mensagemLimpa.split(": ")[1];
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "Erro de validação de parâmetro", mensagemLimpa);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    
    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ExceptionResponse> handleBusinessException(
            BusinessException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "Regra de negócio violada", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "Argumento inválido", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public final ResponseEntity<ExceptionResponse> handleDuplicateResource(
            DuplicateResourceException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), ex.getMessage(), "Duplicate resource");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidGameRuleException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidGameRule(
            InvalidGameRuleException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), ex.getMessage(), "Invalid game rule");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        String message = String.format("Valor inválido '%s' para o parâmetro '%s'. Tipo esperado: %s",
                ex.getValue(), ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), message, "Type mismatch");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "Uma restrição do banco de dados foi violada (ex: conflito de chave estrangeira).", "Data integrity violation");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    // para qualquer erro não mapeado acima
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleGeneralException(
            Exception ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "Ocorreu um erro inesperado no servidor", "Internal server error");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}