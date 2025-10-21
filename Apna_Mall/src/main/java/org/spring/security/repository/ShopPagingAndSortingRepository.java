package org.spring.security.repository;

import org.spring.security.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShopPagingAndSortingRepository extends PagingAndSortingRepository<Shop, Long> {

    Page<Shop> findById(long id);

    Page<List<Shop>> findShopByOwnerEmail(String email, Pageable pageable);

}
