package ufcg.pc.concurrent_store.model.stock;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockUpdateRequest {
    private Integer quantity;
}
