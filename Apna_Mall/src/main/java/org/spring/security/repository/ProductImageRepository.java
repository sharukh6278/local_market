package org.spring.security.repository;

import org.spring.security.entity.shop.Product;

import org.spring.security.entity.shop.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

    void deleteAllByproduct(Product product);

}