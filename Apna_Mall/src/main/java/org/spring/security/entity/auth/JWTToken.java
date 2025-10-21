package org.spring.security.entity.auth;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "jwt_token")
public class JWTToken {

    @Id
    private String email;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tokenCreationTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tokenExpireTime;

    private String tokenType = "Bearer";


}
