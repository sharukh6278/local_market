import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';

import '../api_services/logger_Interceptor.dart';
import '../auth/models/user_model.dart';
import '../screens/product_screen/product_api_client/product_api_client.dart';
import '../screens/user_screen/user_api_client/api_client.dart';

class ApnaShopConstant {
  // static final ApnaShopConstant _apnaShopConstant = ApnaShopConstant._internal();
  //
  // factory ApnaShopConstant() {
  //   return _apnaShopConstant;
  // }
  //
  // ApnaShopConstant._internal();

  static ApnaShopConstant apnaShopConstant = ApnaShopConstant();

  static ApnaShopConstant getApnaShopConstant() {
    return apnaShopConstant;
  }

  late BuildContext context;
  UserModel? userModel;
  String? jwtToken;
  String? loggedInUserEmail;
  final String baseUrl = "http://localhost:8082/";
  static final appName = "Apna Shop";
  final Dio dio = Dio();

  ApiClient getApiClient() {
    dio.interceptors.add(MyInterceptor());
    return ApiClient(dio);
  }

  ProductApiClient getProductApiClient() {
    dio.interceptors.add(MyInterceptor());
    return ProductApiClient(dio);
  }

  void setContext(BuildContext context) {
    this.context = context;
  }

  BuildContext getContext() {
    return context;
  }

  void setUserModel(UserModel userModel) {
    this.userModel = userModel;
  }

  UserModel? getUserModel() {
    return userModel;
  }

  void setJWTToken(jwtToken) {
    this.jwtToken = jwtToken;
  }

  String? getJWTToken() {
    return jwtToken;
  }

  String? getLoggedInUserEmail() {
    return loggedInUserEmail;
  }

  void setLoggedInUserEmail(String email) {
    loggedInUserEmail = email;
  }
}
