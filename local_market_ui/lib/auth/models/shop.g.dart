// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'shop.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Shop _$ShopFromJson(Map<String, dynamic> json) => Shop(
  (json['id'] as num?)?.toInt(),
  json['shopName'] as String?,
  json['ownerEmail'] as String?,
  json['ownerName'] as String?,
  (json['ownerPhoneNumber'] as num?)?.toDouble(),
  (json['shopPhoneNumber'] as num?)?.toDouble(),
  json['imageurl'] as String?,
  (json['shopAlternatePhoneNumber'] as num?)?.toDouble(),
  json['createdDate'] as String?,
  json['updateDate'] as String?,
);

Map<String, dynamic> _$ShopToJson(Shop instance) => <String, dynamic>{
  'id': instance.id,
  'shopName': instance.shopName,
  'ownerEmail': instance.ownerEmail,
  'ownerName': instance.ownerName,
  'ownerPhoneNumber': instance.ownerPhoneNumber,
  'shopPhoneNumber': instance.shopPhoneNumber,
  'shopAlternatePhoneNumber': instance.shopAlternatePhoneNumber,
  'createdDate': instance.createdDate,
  'updateDate': instance.updateDate,
  'imageurl': instance.imageurl,
};
