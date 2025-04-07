package ufcg.pc.concurrent_store.model.stock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockUpdateResponse {
    @Builder.Default
    private String message = "Estoque atualizado.";
    private int remainingStock;
}
