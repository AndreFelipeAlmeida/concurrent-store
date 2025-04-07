package ufcg.pc.concurrent_store.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import ufcg.pc.concurrent_store.exception.ConcurrentException;
import ufcg.pc.concurrent_store.model.product.Product;
import ufcg.pc.concurrent_store.model.sale.Sale;
import ufcg.pc.concurrent_store.model.sale.SaleResponse;

@Repository
public class SaleRepository {
    private final ConcurrentHashMap<String, Sale> sales = new ConcurrentHashMap<>();
    private AtomicInteger totalSales = new AtomicInteger(0);

    public void addSale(Sale sale) {
        totalSales.addAndGet(sale.getQuantitySold().get());

        if (sales.putIfAbsent(sale.getId(), sale) != null) {
            sales.computeIfPresent(sale.getId(), 
            (id, existingSale) -> {
                existingSale.incrementQuantitySold(sale.getQuantitySold());
                return existingSale;
            });
        }
    }

    public Integer getTotalSales() {
        return this.totalSales.get();
    }

    public Enumeration<Sale> getSales() {
        return this.sales.elements();
    }
}
