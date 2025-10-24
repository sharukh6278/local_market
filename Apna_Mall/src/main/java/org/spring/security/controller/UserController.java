package org.spring.security.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.entity.auth.JWTToken;
import org.spring.security.entity.auth.User;
import org.spring.security.entity.shop.Product;
import org.spring.security.model.FridayResponse;
import org.spring.security.model.LoginRequest;
import org.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT}, path = "/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user, @RequestParam("shopId") long shopId) {
        logger.info("UserController:register: {}, and shop name: {}", user,shopId);
        return userService.register(user,shopId);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        logger.info("UserController:register: {}, and shop name: {}", user);
        return userService.updateUser(user);
    }

    @PostMapping(value = "/login")
    public JWTToken login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("UserController :login : {}", loginRequest);
        return userService.login(loginRequest, httpServletRequest, httpServletResponse);
    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam("email") String email) {
        logger.info("UserController :login : {}", email);
        return userService.getUser(email);
    }


    @GetMapping(value = "/logout")
    public FridayResponse logOut(@RequestParam("email") String email) {
        logger.info("UserController :logOut : {}", email);
        return userService.logOut(email);
    }

    @DeleteMapping("/deleteBookById")
    public FridayResponse deleteUserById(@RequestParam("id") Long id) {
        return userService.deleteUserById(id);
    }

    @PostMapping(value = "/updateProfilePhoto")
    public User updateProfilePhoto(@RequestParam("files") MultipartFile[] files,
                                @RequestParam("email") String email
                                ) throws JsonProcessingException {

        logger.info("/updateProfilePhoto :{}", email);
        return userService.updateProfilePhoto(files, email);
    }

    @DeleteMapping("/deleteProfilePhoto")
    public User deleteProfilePhoto(@RequestParam("email") String email) {
        return userService.deleteProfilePhoto(email);
    }

}
