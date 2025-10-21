package org.spring.security.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {


	private String addressId;

    String full_address ; // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
	String city ;
	String state;
    String country;
    long postalCode;
    String knownName ;
    double longitude;
    double latitude;


    @Override
    public String toString() {
        return "Address{" +
                ", addressId='" + addressId + '\'' +
                ", full_address='" + full_address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode=" + postalCode +
                ", knownName='" + knownName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}