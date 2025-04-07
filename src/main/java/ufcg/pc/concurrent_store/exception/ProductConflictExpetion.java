package ufcg.pc.concurrent_store.exception;

public class ProductConflictExpetion extends CommerceException {
    public ProductConflictExpetion() {
        super("Produto com ID já existente.");
    }
}
