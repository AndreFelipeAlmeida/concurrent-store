package ufcg.pc.concurrent_store.model.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponse {
    private String id;
    private String name;
}
