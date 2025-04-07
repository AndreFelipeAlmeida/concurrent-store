package ufcg.pc.concurrent_store.service;

import java.util.Enumeration;
import java.util.concurrent.Callable;

import ufcg.pc.concurrent_store.model.product.*;
import ufcg.pc.concurrent_store.model.purchase.*;
import ufcg.pc.concurrent_store.model.sale.SaleResponse;
import ufcg.pc.concurrent_store.model.stock.*;


public interface IProductService {
    Callable<Enumeration<Product>> getProducts();

    Callable<Product> getProduct(String id);

    Callable<ProductResponseMessage> postProduct(Product product);

    Callable<PurchaseResponse> purchaseProduct(PurchaseRequest request);

    Callable<StockUpdateResponse> updateStock(String id, StockUpdateRequest request);

    Callable<SaleResponse> reportSales();
}

