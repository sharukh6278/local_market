package org.spring.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.entity.auth.JWTToken;
import org.spring.security.config.JwtTokenUtil;
import org.spring.security.entity.auth.User;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.model.FridayResponse;
import org.spring.security.repository.JWTTokenRepository;
import org.spring.security.repository.ShopRepository;
import org.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTTokenService {

    Logger logger = LoggerFactory.getLogger(JWTTokenService.class);

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JWTTokenRepository jwtTokenRepository;

    @Autowired
    UserRepository userRepository;


    public JWTToken generateToken(String email) {
        logger.info(" JWTTokenService: generateToken :email : {}", email);
        User user=userRepository.findByEmail(email);
        String token = jwtTokenUtil.generateToken(user);
        JWTToken jwtToken = new JWTToken();
        jwtToken.setAccessToken(token);
        jwtToken.setTokenCreationTime(new Date());
        jwtToken.setTokenExpireTime(jwtTokenUtil.getAllClaimsFromToken(token).getExpiration());
        jwtToken.setEmail(email);
        return jwtTokenRepository.save(jwtToken);
    }


    public FridayResponse invalidateToken(String email) {
        logger.info(" JWTTokenService: refreshToken :email : {}", email);
        generateToken(email);
        return new FridayResponse("Token is invalidated");
    }

}
