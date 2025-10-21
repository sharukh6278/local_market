package org.spring.security.entity.auth;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.spring.security.entity.MandatoryFields;
import org.spring.security.entity.shop.Shop;
import org.spring.security.enums.UserRegistrationRequestStatus;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "apna_shop_users")
@Entity
@Getter
@Setter
public class User extends MandatoryFields implements Serializable {
    private static final long serialVersionUID = 1L;


    private String profileImageUrl;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column( nullable = true)
    private Boolean userVerified;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "phone",length = 10)
    private long phone;

    @Column(name = "profile_pic_path")
    private String profilePicPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    Shop shop;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<Role> roleList;
}