/*
package org.spring.security.mappers;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.spring.security.model.ShopModel;
import org.spring.security.entity.auth.User;
import org.spring.security.entity.shop.Shop;
import org.spring.security.model.UserModel;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CustomObjectMapper {
    CustomObjectMapper customObjectMapper= Mappers.getMapper(CustomObjectMapper.class);


    @Mappings({
            @Mapping(source = "id",target = "id",qualifiedByName = "StringToObjectId"),
            @Mapping(source = "name",target = "name"),
            @Mapping(source = "email",target = "email"),
            @Mapping(source = "password",target = "password"),
            @Mapping(source = "blocked",target = "blocked"),
            @Mapping(source = "userRegistrationRequestStatus",target = "userRegistrationRequestStatus"),
            @Mapping(source = "roles",target = "roles"),
            @Mapping(source = "productList",target = "productList"),
            @Mapping(source = "jwtToken",target = "jwtToken"),
    })
    User toUser(UserModel userModel);

    @Named("StringToObjectId")
    default ObjectId stringToObjectId(String id){
        return new ObjectId(id);
    }

    @Mappings({
            @Mapping(source = "id",target = "id",qualifiedByName= "ObjectIdToString"),
            @Mapping(source = "name",target = "name"),
            @Mapping(source = "email",target = "email"),
            @Mapping(source = "password",target = "password"),
            @Mapping(source = "blocked",target = "blocked"),
            @Mapping(source = "userRegistrationRequestStatus",target = "userRegistrationRequestStatus"),
            @Mapping(source = "roles",target = "roles"),
            @Mapping(source = "productList",target = "productList"),
            @Mapping(source = "jwtToken",target = "jwtToken"),
    })
    UserModel toUserModel(User user);

    @Named("ObjectIdToString")
    default String objectIdToString(ObjectId objectId){
        return objectId.toString();
    }


    @Mappings({@Mapping(source = "id",target = "shopId",qualifiedByName = "ObjectIdToString"),})
    ShopModel toShopModel(Shop shop);

    @Mappings({@Mapping(source = "shopId",target = "id",qualifiedByName = "StringToObjectId"),})
    Shop toShop(ShopModel shopModel);

}
*/
