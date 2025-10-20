import 'package:flutter/material.dart';


class SpecialProductScreen extends StatefulWidget {
  const SpecialProductScreen({super.key});

  @override
  State<SpecialProductScreen> createState() => _SpecialProductScreenState();


}

class _SpecialProductScreenState extends State<SpecialProductScreen> {

  String homeTitle="Welcome to Apna Mall";
  int selectedTab=1;
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
    return Container(
      child: Center(child: Text("Welcome to Special Product"),),
    );
    // return Scaffold(
    //   appBar: AppBar(
    //     title: Text("Apna Mall",style: TextStyle(color: Colors.blue),),
    //     toolbarHeight: 45,
    //     backgroundColor: Colors.white,
    //
    //   ),
    //   body: Center(child: Text("Welcome to Special Product"),),
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
