package ufcg.pc.concurrent_store.model.product;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.concurrent.locks.ReentrantLock;

@Builder
@Data
public class Product {
    private long ID;
    private String name;
    private Double price;
    private Integer quantity;

    @Getter
    private final transient ReentrantLock lock = new ReentrantLock();

    public boolean purchase(int amount) {
        if (amount <= quantity) {
            quantity -= amount;
            return true;
        }
        return false;
    }
}
