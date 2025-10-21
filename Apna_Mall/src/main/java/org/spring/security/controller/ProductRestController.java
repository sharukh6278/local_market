package org.spring.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.beans.CustomProductPage;
import org.spring.security.beans.CustomShopPage;
import org.spring.security.entity.shop.Product;
import org.spring.security.entity.shop.ProductCategory;
import org.spring.security.entity.shop.ProductImage;
import org.spring.security.model.FridayResponse;
import org.spring.security.model.InputPage;
import org.spring.security.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
        method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT}, path = "/product"
)
public class ProductRestController {
    Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    ProductService productService;

    @Autowired
    private HttpServletRequest httpServletRequest;


//    @PostMapping("/addProduct")
//    public Product addProduct(@RequestParam("files") MultipartFile[] files,
//                              @RequestParam("email") String email,
//                              @RequestParam("title") String title,
//                              @RequestParam("description") String description,
//                              @RequestParam("prize") int prize,
//                              @RequestParam("discountPercentage") float discountPercentage,
//                              @RequestParam("productCateogry") ProductCategory productCateogry,
//                              @RequestParam("customerType") CustomerType customerType,
//                              @RequestParam("material") String material,
//                              @RequestParam("applicableAge") int applicableAge
//                              ) throws JsonProcessingException {
//        logger.info("/addProduct ");
//        Product product = new Product();//(0l,title,description,prize,discountPercentage,productCateogry, customerType,material,applicableAge,null,null,null,null,null);
//        return productService.addProduct(product.toString(), email,files);
//    }

    @PostMapping(value = "/addProduct-v2")
    public Product addProductV2(@RequestParam("files") MultipartFile[] files,
                                @RequestParam("email") String email,
                                @RequestParam("product") String productString,
                                @RequestParam(value = "productId",required = false) Long productId
    ) throws JsonProcessingException {

        logger.info("/addProduct :{}", productString);
        return productService.addProduct(productString, email,files,productId);
    }

    @GetMapping("/getProductListByEmail")
    public Mono<ResponseEntity<CustomProductPage>>  getProductListByEmail(@RequestParam("email") String email, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortDirection") Sort.Direction sortDirection, @RequestParam("sortColumns")  String... sortColumn) {
        logger.info("getBookListByEmail : email : {}", email);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortColumn));
        return Mono.fromCallable(()->productService.getProdcutListByEmail(email,pageable)).map(page->ResponseEntity.ok(page));

    }


    @PostMapping("/getAllProductList")
    public CustomProductPage getAllProductList(@RequestBody InputPage inputPage, @RequestParam("productFetchType") String productFetchType, @RequestParam(value = "shopId",required = false) long shopId,@RequestParam(value = "shopName",required = false) String shopName) {
        logger.info("getAllProductList : shopName : {}", productFetchType);
        Pageable pageable = PageRequest.of(inputPage.getPageNumber(), inputPage.getPageSize());
        return productService.getAllProductList(productFetchType,shopId,shopName,pageable);

    }

    @GetMapping("/getProductById")
    public Optional<Product> getProductById(@RequestParam("id") Long id) {
        //Optional<Product> productOptional = productService.getProductById(id);
        return productService.getProductById(id);
    }

   // @PreAuthorize("hasPermission('updateProduct',#product)")
    @PutMapping("/updateProduct")
    public Product updateProduct(@RequestParam("files") MultipartFile[] files,
                                @RequestParam("email") String email,
                                @RequestParam("product") String productString,
                                @RequestParam(value = "productId",required = true) Long productId
    ) throws JsonProcessingException {

        logger.info("/addProduct :{}", productString);
        return null;//productService.updateProduct(productString, email,files,productId);
    }

    @DeleteMapping("/deleteProduct")
    public FridayResponse deleteProduct(@RequestBody Product product) {
        return productService.deleteProduct(product);
    }

    //    @GetMapping("/getImage")
    //    public List<Product> getImage(@RequestParam("fileName") String fileName) {
    //        logger.info("getImage : getImage : {}", fileName);
    //        return productService.getImage(fileName);
    //    }
    //    String FILE_PATH_ROOT = "/opt/javatodev/images/";

    @GetMapping("/getImage")
    public Mono<ResponseEntity<byte[]>> getImage(@RequestParam("uri") String uri) {
        //String FILE_PATH_ROOT = "/opt/javatodev/images/";
        String FILE_PATH_ROOT= httpServletRequest.getServletContext().getRealPath("/")+uri;
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(FILE_PATH_ROOT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] finalImage = image;
        return Mono.defer(()->Mono.just(ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(finalImage)));
    }

    @GetMapping("/getAllProductCategory")
    public List<ProductCategory> getAllProductCategory() {

        return productService.getAllProductCategory();
    }

    @GetMapping("/getProductByProductCategory")
    public List<ProductCategory> getProductByProductCategory(@RequestParam("name") String name, @RequestParam("shopId") long shopId, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortDirection") Sort.Direction sortDirection, @RequestParam("sortColumns")  String... sortColumn) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortColumn));
        return productService.getProductByProductCategory(name,pageable);
}

    @GetMapping("/getAllProductListByProductCategory")
    public CustomProductPage getAllProductListByProductCategory(@RequestParam("categoryName") String categoryName, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortDirection") Sort.Direction sortDirection, @RequestParam("sortColumns")  String... sortColumn) {
        logger.info("getAllProductListByProductCategory : categoryName : {}", categoryName);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortColumn));
        return productService.getAllProductListByProductCategory(categoryName,pageable);

    }

    @DeleteMapping("/deleteProductImage")
    public FridayResponse deleteProductImage(@RequestParam("id") long id){
        return productService.deleteProductImage(id);
    }

    @PostMapping("/addProductImage")
    public ProductImage addProductImage(@RequestParam("files") MultipartFile[] files, @RequestParam("productId") long productId){
        return productService.addProductImage(files,productId);
    }

}
