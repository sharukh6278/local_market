import 'package:json_annotation/json_annotation.dart';
part 'login.g.dart';

@JsonSerializable()
class Login{
  String? email;
  String? password;

  Login(this.email,this.password);

  factory Login.fromJson(Map<String, dynamic> json) => _$LoginFromJson(json);
  Map<String, dynamic> toJson() => _$LoginToJson(this);

  // Login.fromJson(Map<String, dynamic> json) {
  //   email = json['email'];
  //   password = json['password'];
  // }
  //
  // Map<String, dynamic> toJson() {
  //   final Map<String, dynamic> data = new Map<String, dynamic>();
  //
  //   data['email'] = this.email;
  //   data['password'] = this.password;
  //   return data;
  // }
}