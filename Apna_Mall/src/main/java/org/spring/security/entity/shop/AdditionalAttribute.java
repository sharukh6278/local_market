package org.spring.security.entity.shop;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "additional_attribute")
public class AdditionalAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    private String customkey;
    private String customvalue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
