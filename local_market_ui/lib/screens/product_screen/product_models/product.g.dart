// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'product.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Product _$ProductFromJson(Map<String, dynamic> json) => Product(
  (json['id'] as num?)?.toDouble(),
  json['title'] as String?,
  json['description'] as String?,
  (json['prize'] as num?)?.toInt(),
  (json['discountPercentage'] as num?)?.toDouble(),
  json['productCategory'] as String?,
  json['shop'] == null
      ? null
      : Shop.fromJson(json['shop'] as Map<String, dynamic>),
  json['user'] == null
      ? null
      : UserModel.fromJson(json['user'] as Map<String, dynamic>),
  json['createdDate'] as String?,
  json['updatedDate'] as String?,
  (json['productImageList'] as List<dynamic>?)
      ?.map((e) => ProductImage.fromJson(e as Map<String, dynamic>))
      .toList(),
  (json['additionalAttributeList'] as List<dynamic>?)
      ?.map((e) => AdditionalAttribute.fromJson(e as Map<String, dynamic>))
      .toList(),
  (json['productReviewList'] as List<dynamic>?)
      ?.map((e) => ProductReview.fromJson(e as Map<String, dynamic>))
      .toList(),
);

Map<String, dynamic> _$ProductToJson(Product instance) => <String, dynamic>{
  'id': ?instance.id,
  'title': ?instance.title,
  'description': ?instance.description,
  'prize': ?instance.prize,
  'discountPercentage': ?instance.discountPercentage,
  'productCategory': ?instance.productCategory,
  'shop': ?instance.shop,
  'user': ?instance.user,
  'createdDate': ?instance.createdDate,
  'updatedDate': ?instance.updatedDate,
  'productImageList': ?instance.productImageList,
  'additionalAttributeList': ?instance.additionalAttributeList,
  'productReviewList': ?instance.productReviewList,
};

ProductCategory _$ProductCategoryFromJson(Map<String, dynamic> json) =>
    ProductCategory(
      (json['id'] as num?)?.toDouble(),
      json['categoryName'] as String?,
      json['productSubCategory'] == null
          ? null
          : ProductSubCategory.fromJson(
              json['productSubCategory'] as Map<String, dynamic>,
            ),
    );

Map<String, dynamic> _$ProductCategoryToJson(ProductCategory instance) =>
    <String, dynamic>{
      'id': ?instance.id,
      'categoryName': ?instance.categoryName,
      'productSubCategory': ?instance.productSubCategory,
    };

ProductSubCategory _$ProductSubCategoryFromJson(Map<String, dynamic> json) =>
    ProductSubCategory(
      (json['id'] as num?)?.toDouble(),
      json['productSubCategory'] as String?,
    );

Map<String, dynamic> _$ProductSubCategoryToJson(ProductSubCategory instance) =>
    <String, dynamic>{
      'id': ?instance.id,
      'productSubCategory': ?instance.productSubCategory,
    };

ProductImage _$ProductImageFromJson(Map<String, dynamic> json) => ProductImage(
  (json['id'] as num?)?.toDouble(),
  json['fileName'] as String?,
  json['filePath'] as String?,
  json['product'] == null
      ? null
      : Product.fromJson(json['product'] as Map<String, dynamic>),
  json['apnaShopMediaType'] as String?,
  json['contentType'] as String?,
);

Map<String, dynamic> _$ProductImageToJson(ProductImage instance) =>
    <String, dynamic>{
      'id': ?instance.id,
      'fileName': ?instance.fileName,
      'filePath': ?instance.filePath,
      'product': ?instance.product,
      'apnaShopMediaType': ?instance.apnaShopMediaType,
      'contentType': ?instance.contentType,
    };

AdditionalAttribute _$AdditionalAttributeFromJson(Map<String, dynamic> json) =>
    AdditionalAttribute(
      (json['id'] as num?)?.toDouble(),
      json['customkey'] as String?,
      json['customvalue'] as String?,
      json['product'] == null
          ? null
          : Product.fromJson(json['product'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$AdditionalAttributeToJson(
  AdditionalAttribute instance,
) => <String, dynamic>{
  'id': ?instance.id,
  'customkey': ?instance.customkey,
  'customvalue': ?instance.customvalue,
  'product': ?instance.product,
};

ProductReview _$ProductReviewFromJson(Map<String, dynamic> json) =>
    ProductReview(
      (json['id'] as num?)?.toDouble(),
      json['comment'] as String?,
      (json['rating'] as num?)?.toInt(),
      json['product'] == null
          ? null
          : Product.fromJson(json['product'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$ProductReviewToJson(ProductReview instance) =>
    <String, dynamic>{
      'id': ?instance.id,
      'comment': ?instance.comment,
      'rating': ?instance.rating,
      'product': ?instance.product,
    };
