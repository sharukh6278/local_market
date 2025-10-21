package org.spring.security.config.access;

import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.Shop;
import org.spring.security.repository.ProductRepository;
import org.spring.security.repository.ShopRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("updateProduct")
public class ProductOperationAccessManager implements  AccessManager{
    public static  final String ROLE_OWNER="ROLE_OWNER";
    public static  final String ROLE_MANAGER="ROLE_MANAGER";
    public static  final String ROLE_HELPER="ROLE_HELPER";
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    public ProductOperationAccessManager(ShopRepository shopRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean checkAccess(Authentication authentication, Object data) {
        List<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if(userRoles.contains("ROLE_ADMIN")){
            return true;
        }
        Product product = (Product) data;
        Optional<Product> optionalProduct=productRepository.findById(product.getId());
        if(optionalProduct.isPresent()){
            Shop shop = optionalProduct.get().getShop();
            System.out.println(" shop : " + shop);
            String ROLE_OWNER=String.format("ROLE_%s_OWNER",shop.getId());
            String ROLE_MANAGER=String.format("ROLE_%s_MANAGER",shop.getId());
            String ROLE_HELPER=String.format("ROLE_%s_HELPER",shop.getId());
            if(userRoles.contains(ROLE_OWNER) || userRoles.contains(ROLE_MANAGER) || userRoles.contains(ROLE_HELPER)){
                return true;
            }
        }

        return false;
    }
}
