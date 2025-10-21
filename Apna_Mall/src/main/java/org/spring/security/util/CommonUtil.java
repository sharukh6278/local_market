package org.spring.security.util;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.config.JwtTokenUtil;
import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.ProductImage;
import org.spring.security.entity.shop.Shop;
import org.spring.security.enums.ApnaShopMediaType;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommonUtil {

    Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    final JwtTokenUtil jwtTokenUtil;
    private final HttpServletRequest httpServletRequest;

    @Value("${apna_shop.product_images_path}")
    String productImagesPath;

    @Value("${apna_shop.shop_images_path}")
    String shopImagesPath;

    @Autowired
    ProductImageRepository productImageRepository;

    public CommonUtil(JwtTokenUtil jwtTokenUtil, HttpServletRequest httpServletRequest) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.httpServletRequest = httpServletRequest;
    }

//    public List<ProductImage> saveProductImages(MultipartFile[] files, Product persistedProduct, Shop persistedShop,ApnaShopMediaType apnaShopMediaType,boolean isProductUpdating) {
//        List<ProductImage> productImageList = new ArrayList<>();
//        try {
//            String filePath = httpServletRequest.getServletContext().getRealPath(ApnaShopConstant.FARWARD_SLASH) + (persistedProduct!=null?productImagesPath:shopImagesPath);
//            File file232 = new File(filePath);
//            file232.mkdirs();
//            for (MultipartFile file : files) {
//                String fileContentType = file.getContentType();
//                String fileName = file.getOriginalFilename();
//                File fileToBeCopied = new File(filePath + ApnaShopConstant.FARWARD_SLASH + fileName);
//                file.transferTo(fileToBeCopied);
//
//                ProductImage productImage = new ProductImage(fileName, (persistedProduct!=null?productImagesPath:shopImagesPath)+ ApnaShopConstant.FARWARD_SLASH+fileName, null, apnaShopMediaType, fileContentType);
//                if (isProductUpdating) {
//                    productImage.setProduct(persistedProduct);
//                } else {
//                    productImage.setProduct(null);
//                }
//                productImage.setShop(persistedShop);
//                productImageList.add(productImageRepository.save(productImage));
//                logger.info("file is save :{} ", fileToBeCopied.getAbsolutePath());
//            }
//
//        } catch (Exception e) {
//            logger.error("error in file saving :{}", e.getMessage());
//            throw new ApnaShopException(500, "error while saving product : " + e.getMessage(), e.getCause().toString());
//        }
//        return productImageList;
//    }

//
//    public List<File> uploadFiles(MultipartFile[] files,String destinationFilePath) {
//        List<File> uploadedFileList = new ArrayList<>();
//        try {
//            String filePath = httpServletRequest.getServletContext().getRealPath(ApnaShopConstant.FARWARD_SLASH) + destinationFilePath;
//            File file232 = new File(filePath);
//            file232.mkdirs();
//            for (MultipartFile file : files) {
//                String fileContentType = file.getContentType();
//                String fileName = file.getOriginalFilename();
//                File fileToBeCopied = new File(filePath + ApnaShopConstant.FARWARD_SLASH + fileName);
//                file.transferTo(fileToBeCopied);
//                uploadedFileList.add(file);
//
//            }
//            return uploadedFileList;
//
//        } catch (Exception e) {
//            logger.error("error in file saving :{}", e.getMessage());
//            throw new ApnaShopException(500, "error while saving product : " + e.getMessage(), e.getCause().toString());
//        }
 //   }

}
