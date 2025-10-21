package org.spring.security.model;

import org.spring.security.entity.auth.JWTToken;
import org.spring.security.entity.auth.Role;
import org.spring.security.entity.shop.Product;
import org.spring.security.enums.UserRegistrationRequestStatus;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

    private String id;
    private String userId;

    private String name;
    private String email;

    private String password;
    private boolean blocked;

    private long phone;

    private UserRegistrationRequestStatus userRegistrationRequestStatus;
    private List<Role> roles;


    private JWTToken jwtToken;

    private List<Product> productList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public UserRegistrationRequestStatus getUserRegistrationRequestStatus() {
        return userRegistrationRequestStatus;
    }

    public void setUserRegistrationRequestStatus(UserRegistrationRequestStatus userRegistrationRequestStatus) {
        this.userRegistrationRequestStatus = userRegistrationRequestStatus;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public JWTToken getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(JWTToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

