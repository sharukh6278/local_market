import 'package:json_annotation/json_annotation.dart';

import '../../../auth/models/shop.dart';
import '../../../auth/models/user_model.dart';

part 'product.g.dart';

@JsonSerializable(includeIfNull: false, genericArgumentFactories: true)
class Product {
  double? id;
  String? title;
  String? description;
  int? prize;
  double? discountPercentage;
  String? productCategory;
  Shop? shop;
  UserModel? user;
  String? createdDate;
  String? updatedDate;
  List<ProductImage>? productImageList;
  List<AdditionalAttribute>? additionalAttributeList;
  List<ProductReview>? productReviewList;

  Product(
    this.id,
    this.title,
    this.description,
    this.prize,
    this.discountPercentage,
    this.productCategory,
    this.shop,
    this.user,
    this.createdDate,
    this.updatedDate,
    this.productImageList,
    this.additionalAttributeList,
    this.productReviewList,
  );

  factory Product.fromJson(Map<String, dynamic> json) =>
      _$ProductFromJson(json);

  Map<String, dynamic> toJson() => _$ProductToJson(this);

  @override
  String toString() {
    return 'Product{id: $id, title: $title, description: $description, prize: $prize, discountPercentage: $discountPercentage, productCategory: $productCategory, shop: $shop, user: $user, createdDate: $createdDate, updatedDate: $updatedDate, productImageList: $productImageList, additionalAttributeList: $additionalAttributeList, productReviewList: $productReviewList}';
  }
}

@JsonSerializable(includeIfNull: false, genericArgumentFactories: true)
class ProductCategory {
  double? id;
  String? categoryName;
  ProductSubCategory? productSubCategory;

  ProductCategory(this.id, this.categoryName, this.productSubCategory);

  factory ProductCategory.fromJson(Map<String, dynamic> json) =>
      _$ProductCategoryFromJson(json);

  Map<String, dynamic> toJson() => _$ProductCategoryToJson(this);
}

@JsonSerializable(includeIfNull: false, genericArgumentFactories: true)
class ProductSubCategory {
  double? id;
  String? productSubCategory;

  ProductSubCategory(this.id, this.productSubCategory);

  factory ProductSubCategory.fromJson(Map<String, dynamic> json) =>
      _$ProductSubCategoryFromJson(json);

  Map<String, dynamic> toJson() => _$ProductSubCategoryToJson(this);
}

@JsonSerializable(includeIfNull: false, genericArgumentFactories: true)
class ProductImage {
  double? id;
  String? fileName;
  String? filePath;
  Product? product;
  String? apnaShopMediaType;
  String? contentType;

  ProductImage(
    this.id,
    this.fileName,
    this.filePath,
    this.product,
    this.apnaShopMediaType,
    this.contentType,
  );

  factory ProductImage.fromJson(Map<String, dynamic> json) =>
      _$ProductImageFromJson(json);

  Map<String, dynamic> toJson() => _$ProductImageToJson(this);
}

List<String> productCategoryList = [
  "CLOTHS",
  "BELTS",
  "PURSE",
  "SHIRTS",
  "PANTS",
  "T_SHIRT",
  "UNDER_WEAR",
  "SLEEVELESS",
  "BRA",
];

List<String> productForCustomerList = [
  "MALE",
  "FEMALE",
  "BABY_GIRL",
  "BABY_BOY",
];

@JsonSerializable(includeIfNull: false, genericArgumentFactories: true)
class AdditionalAttribute {
  double? id;
  String? customkey;
  String? customvalue;
  Product? product;

  AdditionalAttribute(this.id, this.customkey, this.customvalue, this.product);

  factory AdditionalAttribute.fromJson(Map<String, dynamic> json) =>
      _$AdditionalAttributeFromJson(json);

  Map<String, dynamic> toJson() => _$AdditionalAttributeToJson(this);

  @override
  String toString() {
    return 'AdditionalAttribute{customkey: $customkey, customvalue: $customvalue}';
  }
}

@JsonSerializable(includeIfNull: false, genericArgumentFactories: true)
class ProductReview {
  double? id;
  String? comment;
  int? rating;
  Product? product;

  ProductReview(this.id, this.comment, this.rating, this.product);

  factory ProductReview.fromJson(Map<String, dynamic> json) =>
      _$ProductReviewFromJson(json);

  Map<String, dynamic> toJson() => _$ProductReviewToJson(this);
}
