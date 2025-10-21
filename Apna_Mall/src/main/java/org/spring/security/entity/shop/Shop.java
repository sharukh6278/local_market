package org.spring.security.entity.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.spring.security.entity.MandatoryFields;
import org.spring.security.entity.auth.Role;
import org.spring.security.entity.auth.User;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "shop")
public class Shop  extends MandatoryFields implements Serializable  {

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String ownerEmail;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private long ownerPhoneNumber;

    @Column(nullable = false)
    private long shopPhoneNumber;

    private String shopImageUrl;

    private long shopAlternatePhoneNumber;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Address address;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<User> userList;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Product> productList;
}
