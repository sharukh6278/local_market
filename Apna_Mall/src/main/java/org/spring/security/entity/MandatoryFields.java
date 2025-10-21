package org.spring.security.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class MandatoryFields {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @Column(name = "created_date",nullable = false,updatable = false)
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    @Column(name = "updated_date",nullable = false)
    private Date updatedDate;
}
