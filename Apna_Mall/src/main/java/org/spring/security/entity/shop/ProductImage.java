package org.spring.security.entity.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.spring.security.entity.MandatoryFields;
import org.spring.security.enums.ApnaShopMediaType;


@Getter
@Setter
@Entity(name = "product_image")
public class ProductImage   extends MandatoryFields {

    private String fileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private ApnaShopMediaType   apnaShopMediaType;

    private String contentType;

    public ProductImage(String fileName, String filePath, Product product, ApnaShopMediaType apnaShopMediaType, String contentType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.product = product;
        this.apnaShopMediaType = apnaShopMediaType;
        this.contentType = contentType;
    }
}
