package org.spring.security.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.entity.auth.JWTToken;
import org.spring.security.entity.auth.User;
import org.spring.security.config.JwtTokenUtil;
import org.spring.security.entity.shop.Image;
import org.spring.security.entity.shop.Shop;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.model.FridayResponse;
import org.spring.security.model.LoginRequest;
import org.spring.security.repository.RoleRepository;
import org.spring.security.repository.ShopRepository;
import org.spring.security.repository.UserRepository;
import org.spring.security.util.ApnaShopConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTTokenService jwtTokenService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private  HttpServletRequest httpServletRequest;
    @Value("${apna_shop.user_images_path}")
    String userImagesPath;


    public User register(User user,long shopId) {
        try{
            logger.info("UserService:register: {}", user);
            Shop shop=shopRepository.findById(shopId);
            user.setPassword(alphaNumericString(6));
            user.setShop(shop);
            userRepository.save(user);
            return user;
        }
        catch (DuplicateKeyException duplicateKeyException){
            throw new ApnaShopException(403, "User is Allready Registered With Email:"+user.getEmail(), duplicateKeyException.getMessage());
        }
        catch (Exception exception){
            throw new ApnaShopException(500, exception.getMessage(), exception.getCause().toString());
        }

    }

    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public JWTToken login(LoginRequest loginRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("UserService:login: {}", loginRequest);
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, roleRepository.findRoleByUserEmail(user.getEmail()));
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            JWTToken jwtToken = jwtTokenService.generateToken(loginRequest.getEmail());
            httpServletResponse.setHeader("token", jwtToken.getAccessToken());
            logger.info("request authenticated and jwt send as response");
            return jwtToken;
        }
        throw new ApnaShopException(403, "Invalid Credential", "Invalid Credential");
    }

    public User getUser(String email) {
        logger.info("UserService:getUser: {}", email);
        User  user=userRepository.findByEmail(email);
        System.out.println(user.getShop());
        return user;
//        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
//
//        String email = null;
//        String jwtToken = null;
//
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                email = jwtTokenUtil.getUsernameFromToken(jwtToken);
//                return userRepository.findByEmail(email);
//            } catch (IllegalArgumentException e) {
//                logger.error("Unable to get JWT Token : {}", e.getMessage());
//                throw new ApnaShopException(403, new Date(), FridayConstant.NOT_VALID_TOKEN, "Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//                throw new ApnaShopException(403, new Date(), FridayConstant.NOT_VALID_TOKEN, "Token has expired");
//
//            } catch (Exception e) {
//                throw new ApnaShopException(403, new Date(), e.getMessage(), e.toString());
//            }
//        } else {
//            logger.warn("JWT Token does not begin with Bearer String");
//            throw new ApnaShopException(403, new Date(), FridayConstant.NOT_VALID_TOKEN, "Token does not begin with Bearer String");
//        }
    }

    public FridayResponse logOut(String email) {
        logger.info("UserService:logOut: {}", email);
        SecurityContextHolder.getContext().setAuthentication(null);
        return jwtTokenService.invalidateToken(email);
    }

    public FridayResponse deleteUserById(Long id) {
        userRepository.deleteById(id);
        return new FridayResponse("Book is Deleted SuccessFully");
    }

    public User updateUser(User user) {
        return null;
    }


    public User updateProfilePhoto(MultipartFile[] files, String email) {
        List<Image> productImageList = new ArrayList<>();
        try {
            String filePath = httpServletRequest.getServletContext().getRealPath(ApnaShopConstant.FARWARD_SLASH) + userImagesPath;
            File file232 = new File(filePath);
            file232.mkdirs();
            for (MultipartFile file : files) {
                String fileContentType = file.getContentType();
                String fileName = file.getOriginalFilename();
                File fileToBeCopied = new File(filePath + ApnaShopConstant.FARWARD_SLASH + fileName);
                file.transferTo(fileToBeCopied);

                User user=userRepository.findByEmail(email);
                user.setProfilePicPath(userImagesPath+ ApnaShopConstant.FARWARD_SLASH+fileName);
                return userRepository.save(user);
            }

        } catch (Exception e) {
            logger.error("error in file saving :{}", e.getMessage());
            throw new ApnaShopException(500, "error while saving product : " + e.getMessage(), e.getCause().toString());
        }
        return null;
    }

    public User deleteProfilePhoto(String email) {
        String filePath = httpServletRequest.getServletContext().getRealPath(ApnaShopConstant.FARWARD_SLASH) + userImagesPath;
        User user=userRepository.findByEmail(email);
        String profilePhotoPath = filePath + ApnaShopConstant.FARWARD_SLASH+user.getProfilePicPath();
        File fileToBeDeleted = new File(profilePhotoPath);
        fileToBeDeleted.delete();
        user.setProfilePicPath(null);
        return  userRepository.save(user);

    }
}
