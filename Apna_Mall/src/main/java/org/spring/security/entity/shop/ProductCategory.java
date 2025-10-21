package org.spring.security.entity.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false, unique = true)
    String name;


    @OneToOne(mappedBy = "productCategory")
    Product product;

//    @OneToOne(mappedBy = "productSubCategory")
//    ProductSubCategory productSubCategory;

}
