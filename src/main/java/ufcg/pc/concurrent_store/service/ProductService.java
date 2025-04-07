package ufcg.pc.concurrent_store.service;

import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufcg.pc.concurrent_store.exception.ConcurrentException;
import ufcg.pc.concurrent_store.model.Product;
import ufcg.pc.concurrent_store.repository.ProductRepository;

@Service
public class ProductService {

    private final ExecutorService executorService;
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ExecutorService executorService, ProductRepository productRepository) {
        this.executorService = executorService;
        this.productRepository = productRepository;
    }

    public Enumeration<Product> getProducts() {
        Future<Enumeration<Product>> products = this.executorService.submit(getProductsCallable());
        try {
            return products.get();
        } catch (InterruptedException | ExecutionException e) {
           throw new ConcurrentException();
        }
    }

    private Callable<Enumeration<Product>> getProductsCallable() {
        return () -> this.productRepository.getProducts();
    }
}
