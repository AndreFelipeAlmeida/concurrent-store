package ufcg.pc.concurrent_store.model.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseMessage {
    @Builder.Default
    private String message = "Produto cadastrado com sucesso.";
    private ProductResponse product;
}
