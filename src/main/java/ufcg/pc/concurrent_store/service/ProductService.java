package ufcg.pc.concurrent_store.service;

import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufcg.pc.concurrent_store.exception.ConcurrentException;
import ufcg.pc.concurrent_store.exception.InsufficientStockException;
import ufcg.pc.concurrent_store.model.product.*;
import ufcg.pc.concurrent_store.model.purchase.*;
import ufcg.pc.concurrent_store.model.sale.Sale;
import ufcg.pc.concurrent_store.model.sale.SaleResponse;
import ufcg.pc.concurrent_store.model.stock.*;
import ufcg.pc.concurrent_store.repository.ProductRepository;
import ufcg.pc.concurrent_store.repository.SaleRepository;
import ufcg.pc.concurrent_store.exception.ProductNotFoundException;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }
    
    @Override
    public Callable<Enumeration<Product>> getProducts() {
        return () -> this.productRepository.getProducts();
    }

    public Callable<Product> getProduct(String id) {
        return () -> this.productRepository.getProduct(id);
    }

    @Override
    public Callable<ProductResponseMessage> postProduct(Product product) {
        return () ->{
        productRepository.addProduct(product);
        ProductResponseMessage response = ProductResponseMessage.builder()
                                            .product(
                                                ProductResponse.builder().
                                                id(product.getId())
                                                .name(product.getName())
                                                .build()
                                            ).build();
        return response;
        };
    }

    @Override
    public Callable<PurchaseResponse> purchaseProduct(PurchaseRequest request){
        return () ->{
            Product product = productRepository.getProduct(request.getId());
            product.getLock().lock();
            try {
                if(product.getQuantity() < request.getQuantity()){
                    throw new InsufficientStockException(product.getQuantity());
                }

                product.setQuantity(product.getQuantity() - request.getQuantity());
                PurchaseResponse purchase = this.buildPurchase(product);
                this.addSale(request, product.getName());

                return purchase;
            } finally {
                product.getLock().unlock();
            }
        };
    }
    
    private PurchaseResponse buildPurchase(Product product) {
        return PurchaseResponse.builder()
            .product(
                ProductPurchaseResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .remainingStock(product.getQuantity())
                .build())
            .build();
    }

    private void addSale(PurchaseRequest request, String productName) {
        this.saleRepository.addSale(Sale.builder()
            .id(request.getId())
            .name(productName)
            .quantitySold(new AtomicInteger(request.getQuantity()))
        .build());
    }

    @Override
    public Callable<StockUpdateResponse> updateStock(String id, StockUpdateRequest request) {
        return () -> {
        boolean update = productRepository.updateStock(id, request.getQuantity());
        
        if(!update) {
            throw new ProductNotFoundException();
        }
        return StockUpdateResponse.builder()
                .remainingStock(request.getQuantity())
                .build();
        };
    }

    @Override
    public Callable<SaleResponse> reportSales() {
        return () -> {
            return SaleResponse.builder()
                .totalSales(this.saleRepository.getTotalSales())
                .products(this.saleRepository.getSales())
            .build();
        };
    }
}
