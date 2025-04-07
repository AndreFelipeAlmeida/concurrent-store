package ufcg.pc.concurrent_store.model.sale;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Sale {
    private String id;
    private String name;
    private AtomicInteger quantitySold;

    public void incrementQuantitySold(AtomicInteger quantity) {
        this.quantitySold.addAndGet(quantity.get());
    }
}
