import 'package:json_annotation/json_annotation.dart';
part 'jwt_token.g.dart';

@JsonSerializable()
class JWTToken {
  
   int? id;

   String?accessToken;

   String?tokenCreationTime;

   String? tokenExpireTime;

   String? tokenType="Bearer";

   String? email;


   JWTToken(this.id, this.accessToken, this.tokenCreationTime, this.tokenExpireTime, this.tokenType, this.email);

   JWTToken.fromJson(Map<String, dynamic> json) {
      id = json['id'];
      accessToken = json['accessToken'];
      tokenCreationTime= json['tokenCreationTime'];
      tokenExpireTime= json['tokenExpireTime'];
     tokenType= json['tokenType'];
     email= json['email'];
   }

   Map<String, dynamic> toJson() {
      final Map<String, dynamic> data = <String, dynamic>{};

      data['id'] = id;
      data['accessToken'] = accessToken;
      data['tokenCreationTime'] = tokenCreationTime;
      data['tokenExpireTime'] = tokenExpireTime;
      data['tokenType'] = tokenType;
      data['email'] = email;
      return data;
   }
}
