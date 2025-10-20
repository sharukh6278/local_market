// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'jwt_token.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

JWTToken _$JWTTokenFromJson(Map<String, dynamic> json) => JWTToken(
  (json['id'] as num?)?.toInt(),
  json['accessToken'] as String?,
  json['tokenCreationTime'] as String?,
  json['tokenExpireTime'] as String?,
  json['tokenType'] as String?,
  json['email'] as String?,
);

Map<String, dynamic> _$JWTTokenToJson(JWTToken instance) => <String, dynamic>{
  'id': instance.id,
  'accessToken': instance.accessToken,
  'tokenCreationTime': instance.tokenCreationTime,
  'tokenExpireTime': instance.tokenExpireTime,
  'tokenType': instance.tokenType,
  'email': instance.email,
};
