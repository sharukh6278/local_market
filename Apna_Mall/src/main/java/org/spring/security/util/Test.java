package org.spring.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.spring.security.entity.shop.Product;
import org.spring.security.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class Test {

    public static void main(String[] args) throws JsonProcessingException {
        String str = "{\"title\":\"a Branded brazers for Men\",\"description\":\"a Branded brazers for Men\",\"prize\":4500,\"discountPercentage\":10.0,\"productCategory\":\"CLOTHS\",\"customerType\":\"MALE\",\"material\":\"pure cotton \",\"productSize\":\"28__29__30__31__32__33__34__35__36__37__38__39__40__41__42__SMALL__MEDIUM__LARGE__X_LARGE__XX_LARGE\",\"specialProduct\":true,\"additionalAttributeList\":[]}";
        System.out.println(str);
        Product p = new ObjectMapper().readValue(str, Product.class);

    }
}
