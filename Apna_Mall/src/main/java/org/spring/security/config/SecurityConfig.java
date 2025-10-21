package org.spring.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.spring.security.exception.ApnaShopException;
import org.spring.security.exception.ErrorMessage;
import org.spring.security.repository.RoleRepository;
import org.spring.security.security.AccessDeniedHandlerCustom;
import org.spring.security.security.JwtAuthenticationEntryPoint;
import org.spring.security.security.JwtRequestFilterDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.IOException;
import java.util.Arrays;

@Configuration
@Order(1000)
public class SecurityConfig  {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilterDemo jwtRequestFilterDemo;

    @Autowired
    RoleRepository repository;


    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests(request -> request.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/docs/**",
                                "/webjars/springfox-swagger-ui/lib/**",
                                "/webjars/**","//webjars/springfox-swagger-ui/css/**","/product/getAllProductList/")
                        .permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers("/product/getImage/**","/api/auth/token", "/api/auth/refresh",
                                "/api/hello", "/api/logout","/user/login", "/user/register", "/jwtToken/validateJWTToken",
                                "/swagger-ui","/getImage", "/shop/getShop", "/user/logout","/shop/getAllShop",
                                "/user/getUser","/product/getProductListByShopId","/product/addProduct-v2",
                                "/product/getProductListByEmail","/product/updateProduct","/product/deleteProduct","/shop/search","/product/getAllProductCategory",
                        "/product/getProductByProductCategory","/product/getAllProductListByProductCategory"
                        )
                        .permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/shop/addShop"))
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated());

        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new AccessDeniedHandlerCustom());
        });
        http.addFilterBefore(jwtRequestFilterDemo, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}