package ufcg.pc.concurrent_store.model.purchase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductPurchaseResponse {
    private Long id;
    private String name;
    private Integer remainingStock;
}