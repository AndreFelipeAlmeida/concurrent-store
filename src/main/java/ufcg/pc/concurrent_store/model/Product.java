package ufcg.pc.concurrent_store.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
    private long ID;
    private String name;
    private Double price;
    private Integer quantity;
}
