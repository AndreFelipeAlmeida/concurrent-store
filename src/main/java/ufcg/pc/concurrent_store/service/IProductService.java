package ufcg.pc.concurrent_store.service;

import java.util.Enumeration;
import java.util.concurrent.Callable;

import ufcg.pc.concurrent_store.model.product.*;
import ufcg.pc.concurrent_store.model.purchase.*;
import ufcg.pc.concurrent_store.model.stockUpdate.*;


public interface IProductService {
    Callable<Enumeration<Product>> getProducts();

    Callable<Product> getProduct(Long id);

    Callable<ProductResponseMessage> postProduct(Product product);

    Callable<PurchaseResponse> purchaseProduct(PurchaseRequest request);

    Callable<StockUpdateResponse> updateStock(Long id, StockUpdateRequest request);

}

