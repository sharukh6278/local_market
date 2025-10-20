import 'package:dio/dio.dart';
import 'package:local_market_ui/common_utils/constants.dart';
import 'package:retrofit/retrofit.dart';

import '../../../auth/models/jwt_token.dart';
import '../../../auth/models/login.dart';
import '../../../auth/models/shop.dart';
import '../../../auth/models/user_model.dart';

part 'api_client.g.dart';

class Apis {
  static const String login = '/user/login';
  static const String getUser = '/user/getUser';
  static const String register = '/user/register';
  static const String addShop = '/shop/addShop';
}

final String baseUrl = ApnaShopConstant().baseUrl;

@RestApi()
abstract class ApiClient {
  factory ApiClient(Dio dio) = _ApiClient;

  @POST(Apis.login)
  Future<JWTToken> login(@Body() Login login);

  @GET(Apis.getUser)
  Future<UserModel> getUser(@Query("email") String email);

  @POST(Apis.register)
  Future<UserModel> register(
    @Body() UserModel userModel,
    @Query("shopName") String shopName,
  );

  @POST(Apis.addShop)
  Future<UserModel> addShop(@Body() Shop shop);
}
