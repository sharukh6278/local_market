import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get_navigation/src/root/get_material_app.dart';
import 'package:local_market_ui/screens/account.dart';
import 'package:local_market_ui/screens/auth_screen/login_screen.dart';
import 'package:local_market_ui/screens/auth_screen/reset_password_screen.dart';
import 'package:local_market_ui/screens/home.dart';
import 'package:local_market_ui/screens/product_category.dart';
import 'package:local_market_ui/screens/product_screen/add_product_screen/add_product_screen.dart';
import 'package:local_market_ui/screens/shop_screen/add_shop_screen.dart';
import 'package:local_market_ui/screens/special_product.dart';
import 'package:local_market_ui/screens/user_screen/add_user_screen/add_user_screen.dart';
import 'package:scaler/scaler.dart';

import 'auth/models/user_model.dart';
import 'common_utils/constants.dart';

void main() => runApp(
  GetMaterialApp(
    title: "this dmeo",
    debugShowCheckedModeBanner: false,
    //home: HomeWidget(),
    theme: ThemeData(
      // Define the default brightness and colors.
      brightness: Brightness.light,
      primaryColor: Colors.lightBlue,
      hintColor: Colors.green,
      appBarTheme: AppBarTheme(
        backgroundColor: Colors.lightBlue,
        toolbarHeight: 50,
        titleTextStyle: TextStyle(
          color: Colors.white,
          fontWeight: FontWeight.bold,
        ),
        iconTheme: IconThemeData(size: 10, color: Colors.white),
        actionsIconTheme: IconThemeData(color: Colors.lightBlue),
      ),
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          foregroundColor: Colors.white,
          backgroundColor: Colors.lightBlue,
        ),
      ),
      floatingActionButtonTheme: FloatingActionButtonThemeData(
        backgroundColor: Colors.lightBlue,
        foregroundColor: Colors.white,
      ),
    ),

    initialRoute: '/',
    routes: {
      '/app': (context) => ApnaShopApp(),
      '/': (context) => ApnaShopApp(),
      '/login': (context) => LoginScreen(),
      '/home': (context) => HomeScreen(),
      '/special_product': (context) => SpecialProductScreen(),
      '/product_category': (context) => ProductCategoryScreen(),
      '/reset_password': (context) => ResetPasswordScreen(),
      '/account': (context) => UserAccountDetailScreen(),
      '/add_product': (context) => AddProductScreen(),
      '/add_or_update_user': (context) => AddUserScreen(),
      '/add_shop': (context) => AddShopScreen(),
    },
  ),
);

class ApnaShopApp extends StatefulWidget {
  const ApnaShopApp({super.key});

  @override
  State<ApnaShopApp> createState() => _ApnaShopAppState();
}

class _ApnaShopAppState extends State<ApnaShopApp> {
  final flutterSecureStorage = FlutterSecureStorage();
  var email;
  var userModel;
  var apnaShopConstant = ApnaShopConstant.getApnaShopConstant();
  bool showInProgress = true;

  @override
  void initState() {
    showInProgress = true;
    callForAsync();
  }

  void callForAsync() {
    flutterSecureStorage.read(key: "user").then((value) {
      print("userModel as json String after retireve form storage :$value");
      UserModel? userModel;
      if (apnaShopConstant.getUserModel() != null) {
        userModel = apnaShopConstant.getUserModel()!;
      } else if (value != null) {
        //userModel = UserModel.fromJson(jsonDecode(value));
      }

      if (userModel != null) {
        setState(() {
          this.userModel = userModel;
          email = userModel?.email;
          showInProgress = false;
        });
      } else {
        Navigator.pushNamed(apnaShopConstant.getContext(), '/login');
      }
    });

    //    UserModel? userModel=apnaShopConstant.getUserModel()!=null?apnaShopConstant.getUserModel(): await apiClient.getUser(this.email);
  }

  @override
  Widget build(BuildContext context) {
    apnaShopConstant.setContext(context);
    final arguments =
        (ModalRoute.of(context)?.settings.arguments ?? <String, dynamic>{})
            as Map;
    email = arguments["email"];
    print("email form storage : $email");

    showInProgress = false;
    return Row(
      children: [
        SizedBox(
          height: Scaler.height(1, context),
          width: Scaler.width(1, context),
          child: Visibility(
            maintainSize: true,
            maintainAnimation: true,
            maintainState: true,
            visible: !showInProgress,
            child: HomeScreen(),
          ),
        ),

        // Container(
        //   height: Scaler.height(0.25, context),
        //   width: Scaler.width(0.25, context),
        //   child: Visibility(
        //     child: CircularProgressIndicator(),
        //     maintainSize: true,
        //     maintainAnimation: true,
        //     maintainState: true,
        //     visible: showInProgress,
        //   ),
        // ),
      ],
    );

    //   FutureBuilder<UserModel>(
    //     future:  apiClient.getUser("srk@gmail.com"),
    //     builder: (context, snapshotUser) {
    //       print("user informatin ins  $email");
    //       if (snapshotUser.connectionState == ConnectionState.waiting) {
    //         return CircularProgressIndicator();
    //       }
    //       else if (snapshotUser.hasError) {
    //         return  Container(
    //           child: Text("hasError  while getting${snapshotUser.hasError.toString()}"),
    //         );
    //       }
    //       else if (snapshotUser.hasData) {
    //         UserModel? userModel = snapshotUser.data;
    //         switch (UserRole.Shop_Owner) {
    //           case UserRole.admin || UserRole.Shop_Owner:
    //             return HomeWidget();
    //
    //           case UserRole.Shop_Helper:
    //             return UserAccountDetails();
    //           default:
    //             return LoginWidget();
    //         }
    //       } else {
    //         return Container(
    //           child: Text("Error  while getting and preparing user info"),
    //         );
    //       }
    //     }
    // );
  }

  @override
  void setState(VoidCallback fn) {}
}
