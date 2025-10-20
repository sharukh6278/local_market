import 'package:flutter/material.dart';

class ProductCategoryScreen extends StatefulWidget {
  const ProductCategoryScreen({super.key});

  @override
  State<ProductCategoryScreen> createState() => _ProductCategoryScreenState();
}

class _ProductCategoryScreenState extends State<ProductCategoryScreen> {

  String homeTitle="Welcome to Apna Mall";
  int selectedTab=2;
  void onTabPres(int index){
    setState(() {selectedTab=index;});
    switch(index){
      case 0:
        Navigator.pushNamed(context, '/home');
        break;
      case 1:
        Navigator.pushNamed(context, '/special_product');
        break;
      case 2:
        Navigator.pushNamed(context, '/product_category');
      case 3:
        Navigator.pushNamed(context, '/home');
      case 4:
        Navigator.pushNamed(context, '/home');
        break;

    }
  }
  @override
  Widget build(BuildContext context) {

    return Center(child: Text("Welcome To Product categoy"),);
    // return Scaffold(
    //   appBar: AppBar(
    //     title: Text("Apna Mall",style: TextStyle(color: Colors.blue),),
    //     toolbarHeight: 45,
    //     backgroundColor: Colors.white,
    //
    //   ),
    //   body: Center(child: Text("Welcome to Product category"),),
    //   //Center(child: Image.asset("assets/images/network.jpeg")),
    //   bottomNavigationBar: BottomNavigationBar(
    //     type: BottomNavigationBarType.fixed,
    //     items: const<BottomNavigationBarItem>[
    //       BottomNavigationBarItem(
    //         icon: Icon(Icons.home),
    //         label: "Home",
    //       ),
    //       BottomNavigationBarItem(
    //         icon: Icon(Icons.explore),
    //         label: "Special",
    //       ),
    //       BottomNavigationBarItem(
    //         icon: Icon(Icons.category),
    //         label: "Category",
    //       ),
    //       BottomNavigationBarItem(
    //         icon: Icon(Icons.person),
    //         label: "Account",
    //       ),
    //       BottomNavigationBarItem(
    //         icon: Icon(Icons.shopping_cart),
    //         label: "Cart",
    //       ),
    //     ],
    //     currentIndex: selectedTab,
    //     onTap: onTabPres,
    //   ),
    //   floatingActionButton: FloatingActionButton(
    //     child: Text("clike"),
    //     onPressed: ()=>{setState(() {homeTitle="this text";})},
    //   ),
    // );
  }
}
