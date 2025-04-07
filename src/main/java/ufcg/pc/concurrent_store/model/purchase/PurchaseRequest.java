package ufcg.pc.concurrent_store.model.purchase;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseRequest {
    private long id;
    private Integer quantity;
}
