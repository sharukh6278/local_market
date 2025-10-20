import 'package:json_annotation/json_annotation.dart';
part 'role.g.dart';

@JsonSerializable()
class Role{
  String? name;
  String?authority;

  Role(this.name,this.authority);

  factory Role.fromJson(Map<String, dynamic> json) => _$RoleFromJson(json);
  Map<String, dynamic> toJson() => _$RoleToJson(this);

  // Role.fromJson(Map<String, dynamic> json) {
  //   authority = json['authority'];
  // }
  //
  // Map<String, dynamic> toJson() {
  //   final Map<String, dynamic> data = new Map<String, dynamic>();
  //
  //   data['authority'] = this.authority;
  //   return data;
  // }
}