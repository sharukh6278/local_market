package org.spring.security.entity.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product_sub_category")
public class ProductSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false, unique = true)
    String subCategoryName;


    @OneToOne(mappedBy = "productSubCategory")
    Product product;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    ProductCategory productCategory;
}
