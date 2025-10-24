import 'package:flutter/material.dart';
import 'package:local_market_ui/auth/models/input_page.dart';
import 'package:local_market_ui/screens/account.dart';
import 'package:local_market_ui/screens/product_category.dart';
import 'package:local_market_ui/screens/product_screen/add_product_screen/product_image_screen.dart';
import 'package:local_market_ui/screens/product_screen/product_models/product.dart';
import 'package:local_market_ui/screens/special_product.dart';
import 'package:scaler/scaler.dart';

import '../common_utils/constants.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  var apnaShopConstant = ApnaShopConstant.getApnaShopConstant();

  late List<Product> productlist;
  int _selectedIndex = 0;
  String floatingButtonLabel = "Add Shop";
  @override
  void initState() {
    callForAsync();
  }

  Future<void> callForAsync() async {
    var joken = apnaShopConstant.getJWTToken();
    print("home in callForAsync");
    List<Product> productlist = await apnaShopConstant
        .getProductApiClient()
        .getProductListByShop(
          new InputPage(0, 20, "DESC", ""),
          "Maharaja",
          "SHOP_NAME",
          -1,
        );
    print("this product linst in callForAsynch : $productlist");
  }

  void _onItemTapped(int index) {
    print("bottom tab is press  : $index");
    setState(() {
      _selectedIndex = index;
    });
    print("bottom tab is press  : $_selectedIndex");
  }

  Map tabDetailsMap = {
    0: {"router": "/add_shop", "label": "Add Shop"},
    1: {"router": "/add_product", "label": "Add Product"},
    2: {"router": "/add_category", "label": "Add Category"},
    3: {"router": "/add_shop", "label": "Add Product"},
    4: {"router": "/add_or_update_user", "label": "Add User"},
  };

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(ApnaShopConstant.appName),
        leading: IconButton(
          icon: Image.asset(
            'assets/images/shops_icon.png',
            height: 50,
            width: 50,
          ),
          onPressed: () {
            print("icon clicked");
            apnaShopConstant.userModel = null;
            apnaShopConstant.loggedInUserEmail = null;
          },
        ),
      ),

      floatingActionButton: Visibility(
        visible: tabDetailsMap[_selectedIndex]['label'].toString().isNotEmpty,
        child: SizedBox(
          height: 35,
          child: FloatingActionButton.extended(
            onPressed: () {
              print("add Product _selectedIndex:**** $_selectedIndex");
              switch (_selectedIndex) {
                case 0:
                  Navigator.pushNamed(context, '/add_shop');
                  break;
                case 1:
                  Navigator.pushNamed(context, '/add_product');
                  break;
                case 2:
                  Navigator.pushNamed(context, '/add_category');
                  break;
                case 3:
                  Navigator.pushNamed(context, '/add_product');
                  break;
                case 4:
                  Navigator.pushNamed(context, '/add_or_update_user');
                  break;
              }
            },
            label: Text("${tabDetailsMap[_selectedIndex]['label']}"),
          ),
        ),
      ),

      body: Center(
        child: IndexedStack(
          index: _selectedIndex,
          children: <Widget>[
            Container(
              child: FutureBuilder<List<Product>>(
                future: apnaShopConstant
                    .getProductApiClient()
                    .getProductListByShop(
                      new InputPage(0, 20, "DESC", ""),
                      "Maharaja",
                      "SHOP_NAME",
                      -1,
                    ),
                builder: (context, snapshot) {
                  print("product info :  ${snapshot.data}");
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return Center(child: CircularProgressIndicator());
                  } else if (snapshot.hasError) {
                    return Container(
                      child: Text(
                        "hasError  while getting${snapshot.hasError.toString()}",
                      ),
                    );
                  } else if (snapshot.hasData) {
                    List<Product>? productList = snapshot.data;
                    return ListView.builder(
                      itemCount: productList?.length,
                      itemBuilder: (context, index) {
                        Product product = productList![index];
                        return Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            SizedBox(
                              height: Scaler.height(0.35, context),
                              width: Scaler.width(0.5, context),
                              child: Container(
                                child: ProductImageScreen(product),
                              ),
                            ),
                            SizedBox(
                              height: Scaler.height(0.35, context),
                              width: Scaler.width(0.5, context),
                              child: Container(
                                child: ProductImageScreen(product),
                              ),
                            ),
                          ],
                        );
                      },
                    );
                  } else {
                    return Container(
                      child: Text(
                        "Error  while getting and preparing user info",
                      ),
                    );
                  }
                },
              ),
            ),

            Container(child: Center(child: SpecialProductScreen())),
            Container(child: Center(child: ProductCategoryScreen())),
            Container(
              child: FutureBuilder<List<Product>>(
                future: apnaShopConstant
                    .getProductApiClient()
                    .getProductListByShop(
                      new InputPage(0, 20, "DSC", ""),
                      "Maharaja",
                      "SHOP_NAME",
                      -1,
                    ),
                builder: (context, snapshot) {
                  print("product info :  ${snapshot.data}");
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return Center(child: CircularProgressIndicator());
                  } else if (snapshot.hasError) {
                    return Container(
                      child: Text(
                        "hasError  while getting${snapshot.hasError.toString()}",
                      ),
                    );
                  } else if (snapshot.hasData) {
                    List<Product>? productList = snapshot.data;
                    return ListView.builder(
                      itemCount: productList?.length,
                      itemBuilder: (context, index) {
                        Product product = productList![index];
                        return Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            SizedBox(
                              height: Scaler.height(0.35, context),
                              width: Scaler.width(0.5, context),
                              child: Container(
                                child: ProductImageScreen(product),
                              ),
                            ),
                            SizedBox(
                              height: Scaler.height(0.35, context),
                              width: Scaler.width(0.5, context),
                              child: Container(
                                child: ProductImageScreen(product),
                              ),
                            ),
                          ],
                        );
                      },
                    );
                  } else {
                    return Container(
                      child: Text(
                        "Error  while getting and preparing user info",
                      ),
                    );
                  }
                },
              ),
            ),
            Container(child: Center(child: UserAccountDetailScreen())),

            // Container(
            //   child: Center(child: ShoppingCart()),
            // ),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(icon: Icon(Icons.home), label: "Home"),
          BottomNavigationBarItem(
            icon: Icon(Icons.accessibility_outlined),
            label: "Products",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.category),
            label: "Category",
          ),
          BottomNavigationBarItem(icon: Icon(Icons.explore), label: "Special"),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: "Account"),
          // BottomNavigationBarItem(
          //   icon: Icon(Icons.shopping_cart),
          //   label: "Cart",
          // ),
        ],
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
      ),
    );
  }
}
