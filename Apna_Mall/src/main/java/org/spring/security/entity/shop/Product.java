package org.spring.security.entity.shop;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.spring.security.entity.MandatoryFields;
import org.spring.security.entity.auth.User;
import org.spring.security.enums.CustomerType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "product")
@Getter
@Setter
public class Product extends MandatoryFields implements Serializable {

    @Column(name = "title", nullable = false)
    private String title;

    private String description;

    @Column(name = "prize", nullable = false)
    private int prize;

    @Column(name = "discountPercentage", length = 100)
    private float discountPercentage;

    private String material;

    private String productSize;

    private boolean specialProduct;

    private boolean isInStock;

    private String color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    List<ProductImage> productImageList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AdditionalAttribute> additionalAttributeList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<ProductReview> productReviewList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    ProductCategory productCategory;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_sub_category_id", referencedColumnName = "id")
    ProductSubCategory productSubCategory;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "parent_id")
//    private List<Product> productColorList = new ArrayList<>();

    public Product() {
    }

}
