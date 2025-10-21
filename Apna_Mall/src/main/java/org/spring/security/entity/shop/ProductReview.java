package org.spring.security.entity.shop;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.spring.security.entity.MandatoryFields;

@Getter
@Setter
@Entity(name = "product_review")
public class ProductReview extends MandatoryFields {


    private String comment;


    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
