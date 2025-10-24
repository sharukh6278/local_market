package org.spring.security.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.beans.CustomShopPage;
import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.Shop;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.model.SearchByProductShopCategory;
import org.spring.security.repository.ProductCategoryRepository;
import org.spring.security.repository.ShopPagingAndSortingRepository;
import org.spring.security.repository.ShopRepository;
import org.spring.security.repository.UserRepository;
import org.spring.security.util.ApnaShopConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class ShopService {

    Logger logger = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    UserRepository userRepository;

    private final HttpServletRequest httpServletRequest;

    @Value("${apna_shop.shop_images_path}")
    private String shopImagePath;

    private final ShopPagingAndSortingRepository shopPagingAndSortingRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ShopService(HttpServletRequest httpServletRequest, ShopPagingAndSortingRepository shopPagingAndSortingRepository) {
        this.httpServletRequest = httpServletRequest;
        this.shopPagingAndSortingRepository = shopPagingAndSortingRepository;
    }

    public Shop getShop(String email) {

        return userRepository.findByEmail(email).getShop();
    }

    public Shop addShop(MultipartFile file,String shopAsString) throws JsonProcessingException {
        Shop shop=new ObjectMapper().readValue(shopAsString,Shop.class);
        logger.info("/addProduct :{}", shop);
        try {

            String filePath = httpServletRequest.getServletContext().getRealPath(ApnaShopConstant.FARWARD_SLASH) + shopImagePath;
            File file232 = new File(filePath);
            file232.mkdirs();
            String fileContentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            File fileToBeCopied = new File(filePath + ApnaShopConstant.FARWARD_SLASH + fileName);
            file.transferTo(fileToBeCopied);

            logger.info("file is save :{} ", fileToBeCopied.getAbsolutePath());
            shop.setShopImageUrl(shopImagePath+ ApnaShopConstant.FARWARD_SLASH+fileName);
            return shopRepository.save(shop);

        } catch (Exception e) {
            logger.error("error in file saving :{}", e.getMessage());
            throw new ApnaShopException(500, "error while saving product : " + e.getMessage(), e.getCause().toString());
        }
    }



    public CustomShopPage getAllShop(String email, Pageable pageable) {
        if(email!=null){
            Page shopPage = shopPagingAndSortingRepository.findShopByOwnerEmail(email,pageable);
            CustomShopPage customShopPage =new CustomShopPage(shopPage.getContent(),shopPage.getNumber(),shopPage.getSize(),shopPage.getTotalPages(),shopPage.getTotalElements(),shopPage.isLast());
            return customShopPage;
        }
        else {
            Page shopPage = shopPagingAndSortingRepository.findAll(pageable);
            CustomShopPage customShopPage =new CustomShopPage(shopPage.getContent(),shopPage.getNumber(),shopPage.getSize(),shopPage.getTotalPages(),shopPage.getTotalElements(),shopPage.isLast());
            return customShopPage;
        }

    }

    public SearchByProductShopCategory search(String key) {
        List<Shop> shopList = shopRepository.findShopByShopNameIsLike(key);
        List< Product> productList=productService.searchForProduct(key);
        SearchByProductShopCategory  searchByProductShopCategory=new SearchByProductShopCategory();
        searchByProductShopCategory.setShopList(shopList);
        searchByProductShopCategory.setProductList(productList);
        searchByProductShopCategory.setProductCategoryList(productCategoryRepository.findProductCategoryByNameContaining(key.toUpperCase()));
        return searchByProductShopCategory;
    }
}
