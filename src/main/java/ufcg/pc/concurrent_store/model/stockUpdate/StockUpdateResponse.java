package ufcg.pc.concurrent_store.model.stockUpdate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockUpdateResponse {
    private String message;
    private Integer remainingStock;
}
