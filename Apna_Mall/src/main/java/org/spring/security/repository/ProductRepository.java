package org.spring.security.repository;

import org.spring.security.entity.shop.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    public void deleteByIdIn(List<String> productIdList);

    List<Product> findProductByTitleIsLike(String productTitle);

    List<Product> findProductByProductCategoryName(String name);
}
