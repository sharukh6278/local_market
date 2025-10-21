package org.spring.security.beans;

import org.spring.security.entity.auth.Role;
import org.spring.security.enums.UserRegistrationRequestStatus;

import java.util.List;

public class JWTTokenModel {

    private String email;
    private UserRegistrationRequestStatus userRegistrationRequestStatus;
    private List<Role> roles;
    private boolean blocked;
    private String shopName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public JWTTokenModel(String email, UserRegistrationRequestStatus userRegistrationRequestStatus, List<Role> roles, boolean blocked, String shopName) {
        this.email = email;
        this.userRegistrationRequestStatus = userRegistrationRequestStatus;
        this.roles = roles;
        this.blocked = blocked;
        this.shopName = shopName;
    }
    public JWTTokenModel(){}
}
