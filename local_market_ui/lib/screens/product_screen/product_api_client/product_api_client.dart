import 'package:dio/dio.dart';
import 'package:local_market_ui/auth/models/input_page.dart';
import 'package:local_market_ui/screens/product_screen/product_models/product.dart';
import 'package:retrofit/retrofit.dart';

part 'product_api_client.g.dart';

class Apis {
  static const String getAllProductList = '/product/getAllProductList';
  static const String addProductV2 = '/product/addProduct-v2';
}

@RestApi()
abstract class ProductApiClient {
  factory ProductApiClient(Dio dio) = _ProductApiClient;

  @POST(Apis.getAllProductList)
  Future<List<Product>> getProductListByShop(
    @Body() InputPage inputPage,
    @Query("shopName") String shopName,
    @Query("productFetchType") String productFetchType,
    @Query("shopId") int shopId,
  );

  @MultiPart()
  @POST(Apis.addProductV2)
  //@Header("Content-Type= application/json")
  Future<List<Product>> addProductV2(
    @Part(name: 'files') List<MultipartFile> files,
    @Part(name: "product") String product,
    @Part(name: 'email') String email,
  );
}
