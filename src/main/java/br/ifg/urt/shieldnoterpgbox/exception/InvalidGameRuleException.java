package br.ifg.urt.shieldnoterpgbox.exception;

public class InvalidGameRuleException extends RuntimeException {
    public InvalidGameRuleException(String message) {
        super(message);
    }
}