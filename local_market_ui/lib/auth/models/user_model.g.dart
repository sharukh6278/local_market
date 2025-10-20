// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserModel _$UserModelFromJson(Map<String, dynamic> json) => UserModel(
  json['email'] as String?,
  json['password'] as String?,
  (json['id'] as num?)?.toInt(),
  json['name'] as String?,
  json['verified'] as bool?,
  json['blocked'] as bool?,
  (json['phone'] as num?)?.toInt(),
  json['userRegistrationRequestStatus'] as String?,
  json['createdDate'] as String?,
  json['updatedDate'] as String?,
  json['shop'] == null
      ? null
      : Shop.fromJson(json['shop'] as Map<String, dynamic>),
  (json['roleList'] as List<dynamic>?)
      ?.map((e) => Role.fromJson(e as Map<String, dynamic>))
      .toList(),
  json['userVerified'] as bool?,
);

Map<String, dynamic> _$UserModelToJson(UserModel instance) => <String, dynamic>{
  'email': instance.email,
  'password': instance.password,
  'id': instance.id,
  'name': instance.name,
  'verified': instance.verified,
  'blocked': instance.blocked,
  'phone': instance.phone,
  'userRegistrationRequestStatus': instance.userRegistrationRequestStatus,
  'createdDate': instance.createdDate,
  'updatedDate': instance.updatedDate,
  'shop': instance.shop,
  'roleList': instance.roleList,
  'userVerified': instance.userVerified,
};
