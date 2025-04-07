package ufcg.pc.concurrent_store.exception;

public class CommerceException extends RuntimeException {
    public CommerceException() {
        super("Erro inesperado!");
    }

    public CommerceException(String message) {
        super(message);
    }
}