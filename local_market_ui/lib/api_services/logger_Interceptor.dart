import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:local_market_ui/common_utils/constants.dart';

class MyInterceptor implements Interceptor {
  final flutterSecureStorage = FlutterSecureStorage();
  var apnaShopConstant = ApnaShopConstant.getApnaShopConstant();
  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    print("got error while calling api : ${err.message}");
    return handler.next(err);
  }

  @override
  Future<void> onRequest(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    Uri url = options.uri;
    options.baseUrl = apnaShopConstant.baseUrl;
    print("requested to url $url");
    print(options.headers);
    var jwtToken = apnaShopConstant.getJWTToken() ?? await flutterSecureStorage.read(key: "jwtToken");
    if (jwtToken != null && !options.path.endsWith("/user/login")) {
      options.headers.addAll({'Authorization': jwtToken});
    }
    return handler.next(options);
  }

  @override
  void onResponse(Response response, ResponseInterceptorHandler handler) {
    print("resposne to url ${response.statusCode}");
    return handler.next(response);
  }
}

// // to use it
//
// final client = HttpClientWithInterceptor.build(
//   interceptors: [MyInterceptor()],
// );
