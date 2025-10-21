// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'input_page.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

InputPage _$InputPageFromJson(Map<String, dynamic> json) => InputPage(
  (json['pageNumber'] as num?)?.toInt(),
  (json['pageSize'] as num?)?.toInt(),
  json['sortDirection'] as String?,
  json['sortColumn'] as String?,
);

Map<String, dynamic> _$InputPageToJson(InputPage instance) => <String, dynamic>{
  'pageNumber': instance.pageNumber,
  'pageSize': instance.pageSize,
  'sortDirection': instance.sortDirection,
  'sortColumn': instance.sortColumn,
};
