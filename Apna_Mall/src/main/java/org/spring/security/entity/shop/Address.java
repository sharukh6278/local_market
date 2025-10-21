package org.spring.security.entity.shop;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    double longitude;
    double latitude;

    String name;
    String street;
    String isoCountryCode;
    String country;
    String postalCode;
    String administrativeArea;
    String subAdministrativeArea;
    String locality;
    String subLocality;
    String thoroughfare;
    String subThoroughfare;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    Shop shop;
}