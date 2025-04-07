package ufcg.pc.concurrent_store.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorType {
    @JsonProperty("message")
    private String message;
    
    public CustomErrorType(CommerceException e) {
        this.message = e.getMessage();
    }

}