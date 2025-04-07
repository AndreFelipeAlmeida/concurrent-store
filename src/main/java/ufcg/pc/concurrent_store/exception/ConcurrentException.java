package ufcg.pc.concurrent_store.exception;


public class ConcurrentException extends CommerceException {
    public ConcurrentException() {
        super("Erro de Concorrência");
    }
}
