package org.spring.security.repository;

import org.spring.security.entity.shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPagingAndSortingRepository  extends PagingAndSortingRepository<Product, Long>
{
    public Page<Product> findProductByShopId(long shopId, Pageable pageable);

    public Page<Product> findProductBySpecialProductAndShopId(boolean selectSpecial,long shopId, Pageable pageable);

    public Page<Product> findProductBySpecialProduct(boolean selectSpecial, Pageable pageable);

    Page<Product> findProductByUser_Email(String email, Pageable pageable);
    public Page<Product> findProductByProductCategoryName(String name, Pageable pageable);



}
