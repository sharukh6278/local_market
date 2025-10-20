import 'package:flutter/material.dart';
import 'package:local_market_ui/common_utils/constants.dart';
import 'package:local_market_ui/screens/product_screen/product_models/product.dart';
import 'package:scaler/scaler.dart';

class ProductImageScreen extends StatefulWidget {
  final Product product;
  const ProductImageScreen(this.product, {super.key});

  @override
  State<ProductImageScreen> createState() => _ProductImageScreenState(product);
}

class _ProductImageScreenState extends State<ProductImageScreen> {
  final Product product;
  _ProductImageScreenState(this.product);
  ApnaShopConstant apnaShopConstant = ApnaShopConstant.getApnaShopConstant();

  @override
  Widget build(BuildContext context) {
    // this.productList..forEach((element) {
    //   print("product in product card : ${element.title}");
    // });
    print("product in product card : ${product.title}");

    return Container(
      child: InkWell(
        splashColor: Colors.lightBlue,
        onTap: () {
          print("car is clicked");
        },
        child: Card(
          child: Column(
            children: [
              SizedBox(
                height: Scaler.height(0.25, context),
                width: Scaler.width(0.45, context),
                child: Container(
                  child: Image.network(
                    "${apnaShopConstant.baseUrl}product/getImage/${product.productImageList?.first.fileName}",
                    width: BorderSide.strokeAlignInside,
                    fit: BoxFit.fill,
                  ),
                ),
              ),

              Center(
                child: ListTile(
                  title: Text("${product.title}"),
                  subtitle: Text("\u{20B9}${product.prize}"),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
