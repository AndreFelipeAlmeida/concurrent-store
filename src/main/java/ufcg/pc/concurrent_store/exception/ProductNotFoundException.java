package ufcg.pc.concurrent_store.exception;

public class ProductNotFoundException extends CommerceException {
    public ProductNotFoundException() {
        super("Produto não encontrado.");
    }
}
