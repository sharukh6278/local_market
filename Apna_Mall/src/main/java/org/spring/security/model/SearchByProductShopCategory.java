package org.spring.security.model;

import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.ProductCategory;
import org.spring.security.entity.shop.Shop;

import java.io.Serializable;
import java.util.List;

public class SearchByProductShopCategory implements Serializable {
    List<Shop> shopList;

    List<Product> productList;

    List<ProductCategory> productCategoryList;

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
