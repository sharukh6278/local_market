import 'package:json_annotation/json_annotation.dart';
import 'package:local_market_ui/auth/models/role.dart';
import 'package:local_market_ui/auth/models/shop.dart';

part 'user_model.g.dart';

@JsonSerializable()
class UserModel {
  String? email;
  String? password;
  int? id;
  String? name;
  bool? verified;
  bool? blocked;
  int? phone;
  String? userRegistrationRequestStatus;
  String? createdDate;
  String? updatedDate;
  Shop? shop;
  List<Role>? roleList;
  bool? userVerified;

  UserModel(
    this.email,
    this.password,
    this.id,
    this.name,
    this.verified,
    this.blocked,
    this.phone,
    this.userRegistrationRequestStatus,
    this.createdDate,
    this.updatedDate,
    this.shop,
    this.roleList,
    this.userVerified,
  );

  factory UserModel.fromJson(Map<String, dynamic> json) =>
      _$UserModelFromJson(json);
  Map<String, dynamic> toJson() => _$UserModelToJson(this);

  @override
  String toString() {
    return 'UserModel{email: $email, password: $password, id: $id, name: $name, verified: $verified, blocked: $blocked, phone: $phone, userRegistrationRequestStatus: $userRegistrationRequestStatus, createdDate: $createdDate, updatedDate: $updatedDate, shop: $shop, roleList: $roleList}';
  }
}
