package ufcg.pc.concurrent_store.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Repository;

import ufcg.pc.concurrent_store.exception.ConcurrentException;
import ufcg.pc.concurrent_store.model.product.Product;
import ufcg.pc.concurrent_store.model.sale.Sale;
import ufcg.pc.concurrent_store.model.sale.SaleResponse;

@Repository
public class SaleRepository {
    private final ConcurrentHashMap<String, Sale> sales = new ConcurrentHashMap<>();

    public void addSale(Sale sale) {
        if (sales.putIfAbsent(sale.getId(), sale) != null) {
            sales.computeIfPresent(sale.getId(), 
            (id, existingSale) -> {
                existingSale.incrementQuantitySold(sale.getQuantitySold());
                return existingSale;
            });
        }
    }

    public Integer size() {
        return this.sales.size();
    }

    public Enumeration<Sale> getSales() {
        return this.sales.elements();
    }
}
