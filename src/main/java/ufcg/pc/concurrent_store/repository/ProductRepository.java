package ufcg.pc.concurrent_store.repository;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import ufcg.pc.concurrent_store.exception.ProductConflictExpetion;
import ufcg.pc.concurrent_store.exception.ProductNotFoundException;
import ufcg.pc.concurrent_store.model.product.*;

@Repository
public class ProductRepository {

    private final ConcurrentHashMap<Long, Product> productStorage = new ConcurrentHashMap<>();

    public Enumeration<Product> getProducts() {
        return productStorage.elements();
    }

    public Product getProduct(Long id) {
        Product product = productStorage.get(id);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        try {
            product.getLock().lock();
            return product;
        }  finally {
            product.getLock().unlock();
        }
    }

    public boolean addProduct(Product product){
        product.getLock().lock();
        Boolean aux = productStorage.putIfAbsent(product.getId(), product) == null;
        product.getLock().unlock();
        if(!aux){
            throw new ProductConflictExpetion();
        }
        return aux;
    }

    public boolean updateStock(Long id, int newQuantity){
        Product product = productStorage.get(id);
        if (product == null){
            return false;
        }
        product.getLock().lock();
        try{
            product.setQuantity(newQuantity);
            return true;
        } finally {
            product.getLock().unlock();
        }
    }

    public boolean purchaseProduct(Long id, int quantity){
        Product product = productStorage.get(id);
        if(product == null){
            return false;
        }
        product.getLock().lock();
        try {
            if (product.getQuantity() >= quantity){
                product.setQuantity(product.getQuantity() - quantity);
                return true;
            }
            return false;
        } finally {
            product.getLock().unlock();
        }
    }

    public boolean exists(Long id){
        return productStorage.containsKey(id);
    }
}
