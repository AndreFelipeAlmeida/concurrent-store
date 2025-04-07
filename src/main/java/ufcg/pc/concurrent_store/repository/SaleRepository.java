package ufcg.pc.concurrent_store.repository;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Repository;

import ufcg.pc.concurrent_store.exception.ConcurrentException;
import ufcg.pc.concurrent_store.model.Sale;

@Repository
public class SaleRepository {
    private final BlockingQueue<Sale> sales = new LinkedBlockingDeque<Sale>();

    public void addSale(Sale sale) {
        try {
            sales.put(sale);
        } catch (InterruptedException e) {
            throw new ConcurrentException();
        }
    }
}
