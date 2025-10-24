package org.spring.security.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.beans.CustomProductPage;
import org.spring.security.entity.auth.User;
import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.ProductCategory;
import org.spring.security.entity.shop.Image;
import org.spring.security.entity.shop.Shop;
import org.spring.security.enums.ApnaShopMediaType;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.model.FridayResponse;
import org.spring.security.model.ProductFetchType;
import org.spring.security.repository.*;
import org.spring.security.util.ApnaShopConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductService {
    private final ProductCategoryRepository productCategoryRepository;
    Logger logger = LoggerFactory.getLogger(ProductService.class);

    final
    ProductRepository productRepository;

    final
    UserRepository userRepository;


    final
    ProductReviewRepository productReviewRepository;

    final
    ShopRepository shopRepository;

    private final HttpServletRequest httpServletRequest;

    private final ImageRepository imageRepository;

    private final ProductPagingAndSortingRepository productPagingAndSortingRepository;
    private  final AdditionalAttributeRepository additionalAttributeRepository;


    @Value("${apna_shop.product_images_path}")
    String productImagesPath;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, ProductReviewRepository productReviewRepository, ShopRepository shopRepository, HttpServletRequest httpServletRequest, ImageRepository imageRepository, ProductPagingAndSortingRepository productPagingAndSortingRepository, ProductCategoryRepository productCategoryRepository, AdditionalAttributeRepository additionalAttributeRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productReviewRepository = productReviewRepository;
        this.shopRepository = shopRepository;
        this.httpServletRequest = httpServletRequest;
        this.imageRepository = imageRepository;
        this.productPagingAndSortingRepository = productPagingAndSortingRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.additionalAttributeRepository = additionalAttributeRepository;
    }

    @Transactional
    public Product addProduct(String productAsString, String email, MultipartFile[] files,Long productId) throws JsonProcessingException {
        if(productId==null){
            Product product=new ObjectMapper().readValue(productAsString,Product.class);
            User user = userRepository.findByEmail(email);
            product.setUser(user);
            Shop shop = user.getShop();
            product.setShop(shop);
            List<Image> unSavedimageList=saveProductImages(files);
            List<Image> imageList=unSavedimageList.stream().peek(image ->image.setProduct(product)).collect(Collectors.toList());
            List<Image> savedImageList=imageRepository.saveAll(imageList);
            product.setImageList(savedImageList);
            return productRepository.save(product);
        }
        else{
            Product product=new ObjectMapper().readValue(productAsString,Product.class);
            User user = userRepository.findByEmail(email);
            product.setUser(user);
            Shop shop = user.getShop();
            product.setShop(shop);
            Optional<Product> persistedProduct=productRepository.findById(productId);
            if(persistedProduct.isEmpty() ){
                throw new ApnaShopException(500,"No Product with id : "+productId,"");
            }
            additionalAttributeRepository.deleteAllByproduct(persistedProduct.get());
            imageRepository.deleteAllByproduct(persistedProduct.get());
            List<Image> unSavedimageList=saveProductImages(files);
            List<Image> imageList=unSavedimageList.stream().peek(image ->image.setProduct(product)).collect(Collectors.toList());
            List<Image> savedImageList=imageRepository.saveAll(imageList);

            product.setImageList(savedImageList);
            product.setId(productId);
            return productRepository.save(product);
        }


    }

    public List<Image> saveProductImages(MultipartFile[] files) {
        List<Image> productImageList = new ArrayList<>();
        try {
            String filePath = httpServletRequest.getServletContext().getRealPath(ApnaShopConstant.FARWARD_SLASH) + productImagesPath;
            File file232 = new File(filePath);
            file232.mkdirs();
            for (MultipartFile file : files) {
                String fileContentType = file.getContentType();
                String fileName = file.getOriginalFilename();
                File fileToBeCopied = new File(filePath + ApnaShopConstant.FARWARD_SLASH + fileName);
                file.transferTo(fileToBeCopied);

                Image productImage = new Image(fileName, productImagesPath+ ApnaShopConstant.FARWARD_SLASH+fileName, null, ApnaShopMediaType.IMAGE, fileContentType);

                productImageList.add(imageRepository.save(productImage));
                logger.info("file is save :{} ", fileToBeCopied.getAbsolutePath());
            }

        } catch (Exception e) {
            logger.error("error in file saving :{}", e.getMessage());
            throw new ApnaShopException(500, "error while saving product : " + e.getMessage(), e.getCause().toString());
        }
        return productImageList;
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

//    public Product updateProduct(Product product)  {
//        logger.info("BookApiService : updateBook(): bookId : {}", product.getId());
//
//        return productRepository.save(product);
//
//        Product product=new ObjectMapper().readValue(productAsString,Product.class);
//        User user = userRepository.findByEmail(email);
//        product.setUser(user);
//        Shop shop = user.getShop();
//        product.setShop(shop);
//        product.setProductImageList(saveProductImages(files));
//        additionalAttributeRepository.deleteAllByproduct(product);
//        return productRepository.save(product);
//    }

    public FridayResponse deleteProduct(Product product) {
  //      Optional<Product> productOptional = getProductById(id);
//        if (productOptional.isPresent()) {
//            List<ProductReview> productReviewList = productOptional.get().getProductReviewList();
//            if (productReviewList != null) {
//                productReviewList.forEach(productReview -> {
//                    productReviewRepository.delete(productReview);
//                });
//            }
//        }
        productRepository.delete(product);
        return new FridayResponse("Product is Deleted SuccessFully");
    }


    public CustomProductPage getAllProductList(String productFetchType, long shopId,String shopName, Pageable pageable) {
        if(productFetchType.equalsIgnoreCase(ProductFetchType.SPECIAL_PRODUCT.name())) {
            Page<Product> productPage=productPagingAndSortingRepository.findProductBySpecialProduct(true,pageable);
            CustomProductPage customProductPage=new CustomProductPage(productPage.getContent(),productPage.getNumber(),productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements(),productPage.isLast());
            return customProductPage;
        }
        else if(productFetchType.equalsIgnoreCase(ProductFetchType.SHOP_ID.name())) {
            Page<Product> productPage=productPagingAndSortingRepository.findProductBySpecialProductAndShopId(false,shopId,pageable);
            CustomProductPage customProductPage=new CustomProductPage(productPage.getContent(),productPage.getNumber(),productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements(),productPage.isLast());
            return customProductPage;
        } else if (productFetchType.equalsIgnoreCase(ProductFetchType.SHOP_ID_AND_SPECIAL_PRODUCT.name())) {
            Page<Product> productPage=productPagingAndSortingRepository.findProductBySpecialProductAndShopId(true,shopId,pageable);
            CustomProductPage customProductPage=new CustomProductPage(productPage.getContent(),productPage.getNumber(),productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements(),productPage.isLast());
            return customProductPage;
        } else if(productFetchType.equalsIgnoreCase(ProductFetchType.SHOP_NAME.name())) {
            Page<Shop> productPage=shopRepository.findShopByShopNameIsLike(shopName,pageable);
            List<Product> productList=new ArrayList<>();
            productPage.getContent().forEach(shop->{
                productList.addAll(shop.getProductList());

            });
            CustomProductPage customProductPage=new CustomProductPage(productList,productPage.getNumber(),productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements(),productPage.isLast());
            return customProductPage;
        }
        else {
            Page<Product> productPage=productPagingAndSortingRepository.findAll(pageable);
            CustomProductPage customProductPage=new CustomProductPage(productPage.getContent(),productPage.getNumber(),productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements(),productPage.isLast());
            return customProductPage;
        }
    }


    public CustomProductPage getProdcutListByEmail(String email, Pageable pageable) {
        Page<Product> productPage=productPagingAndSortingRepository.findProductByUser_Email(email,pageable);
        CustomProductPage customProductPage=new CustomProductPage(productPage.getContent(),productPage.getNumber(),productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements(),productPage.isLast());
        return customProductPage;
    }

    public List<Product> searchForProduct(String key) {
        return  productRepository.findProductByTitleIsLike(key);
    }

    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    public List<ProductCategory> getProductByProductCategory(String name, Pageable pageable) {
        return productCategoryRepository.findProductCategoryByNameContaining(name,pageable);

    }

    public CustomProductPage getAllProductListByProductCategory(String categoryName, Pageable pageable) {
        Page<Product> productPage=productPagingAndSortingRepository.findProductByProductCategoryName(categoryName,pageable);
        CustomProductPage customProductPage=new CustomProductPage(productPage.getContent(),productPage.getNumber(),productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements(),productPage.isLast());
        return customProductPage;
    }

    public FridayResponse deleteProductImage(long id) {
        Optional<Image> optionalProductImage=imageRepository.findById(id);
        if(optionalProductImage.isEmpty()){
            throw new ApnaShopException(403,"No Porduct Image for id : "+id,"");
        }
        File file=new File(optionalProductImage.get().getFilePath());
        file.deleteOnExit();
        imageRepository.delete(optionalProductImage.get());
        return new FridayResponse(null,"Product Image is Deleted");
    }

    public Image addProductImage(MultipartFile[] files, long productId) {
        Optional<Product> optionalProduct=productRepository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ApnaShopException(403,"No Product found with product id : "+productId,"");
        }
        List<Image> productImageList=saveProductImages(files);
        Image productImage=productImageList.get(0);
        productImage.setProduct(optionalProduct.get());
        return imageRepository.save(productImage);
    }
}
