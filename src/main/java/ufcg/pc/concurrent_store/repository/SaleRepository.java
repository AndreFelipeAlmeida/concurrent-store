package ufcg.pc.concurrent_store.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Repository;

import ufcg.pc.concurrent_store.exception.ConcurrentException;
import ufcg.pc.concurrent_store.model.sale.Sale;
import ufcg.pc.concurrent_store.model.sale.SaleResponse;

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

    public Integer size() {
        return this.sales.size();
    }

    public BlockingQueue<Sale> getSales() {
        return this.sales;
    }
}
