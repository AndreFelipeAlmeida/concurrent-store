package ufcg.pc.concurrent_store.repository;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import ufcg.pc.concurrent_store.model.Product;

@Repository
public class ProductRepository {

    private final ConcurrentHashMap<Long, Product> productStorage = new ConcurrentHashMap<>();

    public Enumeration<Product> getProducts() {
        return productStorage.elements();
    }
}
