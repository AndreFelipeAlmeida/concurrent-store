package ufcg.pc.concurrent_store.controller;

import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import java.util.concurrent.Future;

import ufcg.pc.concurrent_store.exception.CommerceException;
import ufcg.pc.concurrent_store.exception.ConcurrentException;
import ufcg.pc.concurrent_store.exception.InsufficientStockException;
import ufcg.pc.concurrent_store.exception.ProductConflictExpetion;
import ufcg.pc.concurrent_store.exception.ProductNotFoundException;
import ufcg.pc.concurrent_store.model.product.*;
import ufcg.pc.concurrent_store.service.IProductService;
import ufcg.pc.concurrent_store.service.ProductService;
import ufcg.pc.concurrent_store.model.purchase.*;
import ufcg.pc.concurrent_store.model.sale.SaleResponse;
import ufcg.pc.concurrent_store.model.stock.*;

@RestController
@RequestMapping()
public class ProductController {

	private final IProductService productService;
    private final ExecutorService executorService;
	
	@Autowired
	public ProductController(IProductService productService, ExecutorService executorService) {
		this.productService = productService;
        this.executorService = executorService;
	}

	private CommerceException ThrowExceptions(Exception e) {
		if (e instanceof InterruptedException) {
			Thread.currentThread().interrupt();
		}
		Throwable cause = e.getCause();
		if (cause instanceof ProductConflictExpetion) {
			return (ProductConflictExpetion) cause;
		} 
		else if (cause instanceof ProductNotFoundException) {
			return (ProductNotFoundException) cause;
		}
		else if (cause instanceof InsufficientStockException) {
			return (InsufficientStockException) cause;
		}
		return new ConcurrentException();
	}


	@GetMapping("/products")
	public ResponseEntity<Enumeration<Product>> getProducts() {
		Future<Enumeration<Product>> products = this.executorService.submit(
													this.productService.getProducts());
		try {
			return ResponseEntity.ok(products.get());
        } catch (Exception e) {
			throw this.ThrowExceptions(e);
		}
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable String id) {
		Future<Product> product = this.executorService.submit(
													this.productService.getProduct(id));
		try {
			return ResponseEntity.ok(product.get());
        } catch (Exception e) {
			throw this.ThrowExceptions(e);
		}
	}

	@PostMapping("/products")
	public ResponseEntity<?> postProduct(@RequestBody Product request){
		Future<ProductResponseMessage> response = this.executorService.submit(this.productService.postProduct(request));
		try {
        	ProductResponseMessage response2 = response.get();
        	return ResponseEntity.status(HttpStatus.CREATED).body(response2);
        } catch (Exception e) {
			throw this.ThrowExceptions(e);
		}
	}
	
	@PostMapping("/purchase")
	public ResponseEntity<PurchaseResponse> purchaseProduct(@RequestBody PurchaseRequest request) {
    	Future<PurchaseResponse> future = executorService.submit(
            productService.purchaseProduct(request));

		try{
			return ResponseEntity.status(HttpStatus.CREATED).body(future.get());
        } catch (Exception e) {
			throw this.ThrowExceptions(e);
		}
	}

	@PutMapping("/products/{id}/stock")
	public ResponseEntity<StockUpdateResponse> updateStock(@PathVariable String id, @RequestBody StockUpdateRequest request) {
		Future<StockUpdateResponse> future = executorService.submit(
            productService.updateStock(id, request));

		try {
			return ResponseEntity.ok(future.get());
		} catch (Exception e) {
			throw this.ThrowExceptions(e);
		}
	}

	@GetMapping("/sales/report")
	public ResponseEntity<SaleResponse> reportSales() {
		Future<SaleResponse> future = executorService.submit(
            productService.reportSales());

		try {
			return ResponseEntity.ok(future.get());
		} catch (Exception e) {
			throw this.ThrowExceptions(e);
		}
	}
}
