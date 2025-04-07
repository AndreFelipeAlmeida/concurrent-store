package ufcg.pc.concurrent_store.controller;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufcg.pc.concurrent_store.model.Product;
import ufcg.pc.concurrent_store.service.ProductService;

@RestController
@RequestMapping(
	value = "/products"
)
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<Enumeration<Product>> getProducts() {
		return ResponseEntity
				.ok(this.productService.getProducts());
	}
}
