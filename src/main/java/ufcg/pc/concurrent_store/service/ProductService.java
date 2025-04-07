package ufcg.pc.concurrent_store.service;

import org.springframework.stereotype.Service;

import ufcg.pc.concurrent_store.model.Product;

@Service
public class ProductService {
    public Product[] getProducts() {
        Product[] products = new Product[1];

        products[0] = Product.builder()
                .ID(0)
                .name("Camisa Verde")
                .price(100.0)
                .quantity(30)
                .build();

        return products;
    }
}
