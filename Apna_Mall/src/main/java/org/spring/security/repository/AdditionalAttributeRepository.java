package org.spring.security.repository;

import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalAttributeRepository extends JpaRepository<Image,Long> {
    void deleteAllByproduct(Product product);

}
