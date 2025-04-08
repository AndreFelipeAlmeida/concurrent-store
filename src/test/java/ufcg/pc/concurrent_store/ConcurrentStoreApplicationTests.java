package ufcg.pc.concurrent_store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import ufcg.pc.concurrent_store.model.product.Product;
import ufcg.pc.concurrent_store.model.product.ProductResponseMessage;

import java.util.*;
import java.util.concurrent.*;

@SpringBootTest
class ConcurrentStoreApplicationTests {

    private static final String BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private final int THREAD_COUNT = 10;

    @Test
    void contextLoads() {
        // Apenas valida que o contexto inicia sem erros
    }

    @Test
    void testConcurrentRequests() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 5; j++) {
                        int action = new Random().nextInt(5);
                        switch (action) {
                            case 1 -> getProducts();
                            case 4 -> purchaseProduct("9012"+j, 1);
                            case 2 -> updateStock("9012"+j, 10);
                            case 3 -> getSalesReport();
                            case 0 -> addNewProduct("9012"+j, "Produto X"+j, 9.99 ,10);
                        }
                        Thread.sleep(200);
                    }
                } catch (Exception e) {
                    System.err.println("Erro: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("Teste de concorrência finalizado.");
    }

    private void addNewProduct(String id, String name, double price, int quantity) {
        try {
            Product product = Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity)
            .build();
            ResponseEntity<ProductResponseMessage> response = restTemplate.postForEntity(
                BASE_URL + "/products", product, ProductResponseMessage.class
            );
            System.out.println("[POST /products] " + response.getStatusCode() + " " + response.getBody());
        } catch (Exception e) {
            System.err.println("Erro em /products: " + e.getMessage());
        }
    }

    private void getProducts() {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/products", String.class);
        System.out.println("[GET /products] " + response.getStatusCode() + ' ' + response.getBody());
    }

    private void purchaseProduct(String id, int quantity) {
        String url = BASE_URL + "/purchase";
        Map<String, Object> body = Map.of("id", id, "quantity", quantity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("[POST /purchase] " + response.getStatusCode() + ' ' + response.getBody());
        } catch (Exception e) {
            System.err.println("Erro em /purchase: " + e.getMessage());
        }
    }

    private void updateStock(String id, int quantity) {
        String url = BASE_URL + "/products/" + id + "/stock";
        Map<String, Object> body = Map.of("quantity", quantity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.put(url, request);
            System.out.println("[PUT /stock] OK");
        } catch (Exception e) {
            System.err.println("Erro em /stock: " + e.getMessage());
        }
    }

    private void getSalesReport() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/sales/report", String.class);
            System.out.println("[GET /sales/report] " + response.getStatusCode()+ ' ' +response.getBody());
        } catch (Exception e) {
            System.err.println("Erro em /sales/report: " + e.getMessage());
        }
    }

}
