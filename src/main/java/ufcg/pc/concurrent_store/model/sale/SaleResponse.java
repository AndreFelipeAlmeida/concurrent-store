package ufcg.pc.concurrent_store.model.sale;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleResponse {
    private Integer totalSales;
    private BlockingQueue<Sale> products;
}
