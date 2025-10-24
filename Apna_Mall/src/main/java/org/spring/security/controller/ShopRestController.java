package org.spring.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.beans.CustomShopPage;
import org.spring.security.entity.shop.Shop;
import org.spring.security.model.SearchByProductShopCategory;
import org.spring.security.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT}, path = "/shop")
public class ShopRestController {

    Logger logger = LoggerFactory.getLogger(ShopRestController.class);

    @Autowired
    ShopService shopService;

    @GetMapping("/getShop")
    public Mono<ResponseEntity<Shop>> getShop(@RequestParam("email") String email) {
        logger.info("UserController :login : {}", email);
        return Mono.fromCallable(()->shopService.getShop(email))
                .map(shop-> ResponseEntity.ok(shop));
    }

    @PostMapping("/addShop")
   public Shop addShop(@RequestParam("files") MultipartFile[] files, @RequestParam("shop") String shopString,@RequestParam(value = "shopId",required = false) long shopId) throws JsonProcessingException {
        //return Mono.fromCallable(()->shopService.addShop(files.get(0),shopString))
          //      .map(savedShop-> ResponseEntity.ok(savedShop));
        return shopService.addShop(files,shopString,shopId);
    }

    @GetMapping("/getAllShop")
    public Mono<ResponseEntity<CustomShopPage>> getAllShop(@RequestParam(value = "email",required = false) String email, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortDirection") Sort.Direction sortDirection, @RequestParam("sortColumns")  String... sortColumn) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortColumn));
        return Mono.fromCallable(()->shopService.getAllShop(email,pageable)).map(page ->ResponseEntity.ok(page));
    }

    @GetMapping("/search")
    public SearchByProductShopCategory search(@RequestParam(value = "key",required = true) String key) {

        return shopService.search(key);//Mono.fromCallable(()->shopService.search(key)).map(searchByProductShopCategory ->ResponseEntity.ok(searchByProductShopCategory));
    }


}
