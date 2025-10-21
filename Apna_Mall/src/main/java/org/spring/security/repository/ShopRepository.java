package org.spring.security.repository;


import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findById(long id);

    List<Shop> findShopByOwnerEmail(String email);

    Shop findShopByProductList(List<Product> productList);

    List<Shop> findShopByShopNameIsLike(String shopName);

    Page<Shop> findShopByShopNameIsLike(String shopName, Pageable pageable);

}
