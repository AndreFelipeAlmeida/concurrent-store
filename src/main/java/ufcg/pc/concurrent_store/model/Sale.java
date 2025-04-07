package ufcg.pc.concurrent_store.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Sale {
    private long id;
    private String name;
    private Integer quantitySold;
}
