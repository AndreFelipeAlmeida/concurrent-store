package ufcg.pc.concurrent_store.model.purchase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseResponse {
    @Builder.Default
    private String message = "Compra realizada com sucesso";;
    private ProductPurchaseResponse product;
}
