package org.spring.security.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.spring.security.entity.auth.JWTToken;
import org.spring.security.entity.auth.User;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.repository.JWTTokenRepository;
import org.spring.security.repository.RoleRepository;
import org.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5*(1000 * 60 * 60 * 24);
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JWTTokenRepository jwtTokenRepository;

    @Value("${jwt.secret}")
    private String secret;

    public JwtTokenUtil(RoleRepository roleRepository, UserRepository userRepository, JWTTokenRepository jwtTokenRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtTokenRepository = jwtTokenRepository;
    }


    //for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    public User validateJTWTokenAndGetUser(String token) throws JsonProcessingException {
        JWTToken jwtToken=jwtTokenRepository.findByAccessToken(token);
        if(jwtToken!=null){
            Claims claims=getAllClaimsFromToken(token);
            if(claims!=null){
                String email = claims.get("email", String.class);
                return userRepository.findByEmail(email);
            }
        }
        throw new ApnaShopException(401,"the token is not valid","");
    }


    //generate token for user
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("roles", roleRepository.findRoleByUserEmail(user.getEmail()));
        claims.put("blocked", user.isBlocked());
        claims.put("shopName", user.getShop()!=null?user.getShop().getShopName():null);
        return doGenerateToken(claims, claims.toString());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

}