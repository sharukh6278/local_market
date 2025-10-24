package org.spring.security.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.beans.CustomShopPage;
import org.spring.security.entity.auth.User;
import org.spring.security.entity.shop.Image;
import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.Shop;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.model.SearchByProductShopCategory;
import org.spring.security.repository.*;
import org.spring.security.util.ApnaShopConstant;
import org.spring.security.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private  ImageRepository imageRepository;


    public ShopService(HttpServletRequest httpServletRequest, ShopPagingAndSortingRepository shopPagingAndSortingRepository) {
        this.httpServletRequest = httpServletRequest;
        this.shopPagingAndSortingRepository = shopPagingAndSortingRepository;
    }

    public Shop getShop(String email) {

        return userRepository.findByEmail(email).getShop();
    }

    public Shop addShop(MultipartFile[] files,String shopAsString,long shopId) throws JsonProcessingException {
        Shop shop=new ObjectMapper().readValue(shopAsString,Shop.class);
        logger.info("/addProduct :{}", shop);
        if(shopId==0){
            List<Image> unSavedimageList=commonUtil.saveImages(files);
            List<Image> imageList=unSavedimageList.stream().peek(image ->image.setShop(shop)).collect(Collectors.toList());
            List<Image> savedImageList=imageRepository.saveAll(imageList);
            shop.setImageList(savedImageList);
            return shopRepository.save(shop);
        }
        else{
            Shop savedshop = shopRepository.findById(shopId);
            if(savedshop==null ){
                throw new ApnaShopException(500,"No Shop with id : "+shopId,"");
            }
            if(shop.getAddress()!=null && shop.getAddress().getPostalCode()!=savedshop.getAddress().getPostalCode()){
                savedshop.setAddress(shop.getAddress());
            }
            savedshop.setOwnerEmail(shop.getOwnerEmail());
            savedshop.setOwnerName(shop.getOwnerName());
            savedshop.setOwnerPhoneNumber(shop.getOwnerPhoneNumber());
            savedshop.setShopPhoneNumber(shop.getShopPhoneNumber());
            savedshop.setShopAlternatePhoneNumber(shop.getShopAlternatePhoneNumber());

            imageRepository.deleteAll(savedshop.getImageList());
            List<Image> unSavedimageList=commonUtil.saveImages(files);
            List<Image> imageList=unSavedimageList.stream().peek(image ->image.setShop(savedshop)).collect(Collectors.toList());
            imageRepository.saveAll(imageList);
            return shopRepository.save(shop);
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
