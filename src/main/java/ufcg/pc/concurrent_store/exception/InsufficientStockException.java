package ufcg.pc.concurrent_store.exception;

public class InsufficientStockException extends CommerceException {
    public InsufficientStockException(Integer quantity) {
        super("Estoque insuficiente. Quantidade disponível: " + quantity);
    }
}
