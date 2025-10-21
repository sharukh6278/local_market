package org.spring.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FridayResponse {
    private Object data;
    private String message;

    public FridayResponse(){}

    public FridayResponse(String message) {
        this.message = message;
    }

    public FridayResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    @Override
    public String toString() {
        return "FridayResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
