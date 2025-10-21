
package org.spring.security.security;

import java.io.IOException;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.beans.JWTTokenModel;
import org.spring.security.entity.auth.User;
import org.spring.security.config.JwtTokenUtil;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.repository.RoleRepository;
import org.spring.security.repository.UserRepository;
import org.spring.security.util.ApnaShopConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilterDemo extends OncePerRequestFilter {
    Logger logger1 = LoggerFactory.getLogger(JwtRequestFilterDemo.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String url= String.valueOf(request.getRequestURL());
        logger1.info("request full url  :  {} ",url );


        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                User user=jwtTokenUtil.validateJTWTokenAndGetUser(jwtToken);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getRoleList());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            catch (ApnaShopException apnaShopException) {
                if(apnaShopException.getStatusCode()== HttpStatus.UNAUTHORIZED.value()){
                    throw new ApnaShopException(401, "Token has expired", "Token has expired");
                }
                throw apnaShopException;
            }
            catch (MalformedJwtException malformedJwtException) {
                logger1.error("Unable to get JWT Token : {}", malformedJwtException.getMessage());
                throw new ApnaShopException(401, "Invalid Token, please login With Valid Credntial", "Invalid Token, please login With Valid Credntial");
            } catch (ExpiredJwtException e) {
                logger1.error("403 exception : ",e.getMessage());
                throw new ApnaShopException(401, "Token has expired", "Token has expired");

            } catch (Exception e) {
                logger1.error("403 exception : ",e.getMessage());
                throw new ApnaShopException(500, e.getMessage(), e.toString());
            }
        }
        else {
            logger.warn("JWT Token does not begin with Bearer String");
            throw new ApnaShopException(401, ApnaShopConstant.NOT_VALID_TOKEN, "Token does not begin with Bearer String");
        }

        // Once we get the token validate it.
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            User user = this.userRepository.findByEmail(email);
//
//            // if token is valid configure Spring Security to manually set
//            // authentication
//            if (jwtTokenUtil.validateToken(jwtToken, user.getEmail())) {
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                // After setting the Authentication in the context, we specify
//                // that the current user is authenticated. So it passes the
//                // Spring Security Configurations successfully.
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
        logger1.info("token is valid passing to api call");
        chain.doFilter(request, response);
    }



}
