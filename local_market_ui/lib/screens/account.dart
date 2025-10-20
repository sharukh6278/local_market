import 'package:flutter/material.dart';

class UserAccountDetailScreen extends StatefulWidget {
  const UserAccountDetailScreen({super.key});

  @override
  State<UserAccountDetailScreen> createState() => _UserAccountDetailScreenState();
}

class _UserAccountDetailScreenState extends State<UserAccountDetailScreen> {

  @override
  Widget build(BuildContext context) {

    // checkIfAuthenticated().then((success) {
    //   if (success) {
    //     Navigator.pushReplacementNamed(context, '/home');
    //   } else {
    //     Navigator.pushReplacementNamed(context, '/login');
    //   }
    // });

    return Container(
      child: Center(child: Text("Welcome to account"),),
    );
  }
}
