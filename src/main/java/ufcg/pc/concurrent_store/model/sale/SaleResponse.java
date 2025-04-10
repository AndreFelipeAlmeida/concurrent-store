package ufcg.pc.concurrent_store.model.sale;

import java.util.Enumeration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleResponse {
    private Integer totalSales;
    private Enumeration<Sale> products;
}
