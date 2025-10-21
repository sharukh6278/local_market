package org.spring.security.repository;


import org.spring.security.entity.auth.JWTToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JWTTokenRepository extends JpaRepository<JWTToken, Long> {
    JWTToken findByEmailAndAccessToken(String email,String accessToken);
    void deleteByEmail(String email);
    JWTToken findByAccessToken(String accessToken);

}
