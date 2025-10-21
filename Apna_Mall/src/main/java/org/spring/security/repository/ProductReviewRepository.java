package org.spring.security.repository;

import org.spring.security.entity.shop.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    public void deleteByIdIn(List<String> productIdList);

}
