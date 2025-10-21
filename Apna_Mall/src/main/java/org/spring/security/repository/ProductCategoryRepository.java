package org.spring.security.repository;

import org.spring.security.entity.shop.ProductCategory;
import org.spring.security.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findProductCategoryByNameContaining(String name, Pageable pageable);

    List<ProductCategory> findProductCategoryByNameContaining(String name);

}
